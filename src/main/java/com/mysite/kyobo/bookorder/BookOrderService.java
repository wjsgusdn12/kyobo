package com.mysite.kyobo.bookorder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mysite.kyobo.bookorderdetail.BookOrderDetail;
import com.mysite.kyobo.bookorderdetail.BookOrderDetailRepository;
import com.mysite.kyobo.cart.Cart;
import com.mysite.kyobo.cart.CartRepository;
import com.mysite.kyobo.cart.CartService;
import com.mysite.kyobo.cartItem.CartItem;
import com.mysite.kyobo.cartItem.CartItemRepository;
import com.mysite.kyobo.detail.Detail;
import com.mysite.kyobo.detail.DetailRepository;
import com.mysite.kyobo.member.Member;
import com.mysite.kyobo.member.MemberRepository;
import com.mysite.kyobo.member.MemberService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookOrderService {
	private final BookOrderRepository bookOrderRepository;
	private final CartService cartService;
	private final MemberService memberService;
	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepostiroy;
	private final BookOrderDetailRepository bookOrderDetailRepository;
	private final MemberRepository memberRepository;
	private final DetailRepository detailRepository;
	
	// principal 을 통해 주문목록 cartList 띄워주기
	public List<CartItem> getCartItem(String id) {
		Optional<Member> memberOpt = this.memberService.findById(id);
		Member member = null;
		if (memberOpt.isPresent()) {
			member = memberOpt.get();
		} else {
			throw new IllegalArgumentException("해당된 멤버 없음");
		}
		// 멤버를 통해서 cart 를 찾아야한다
		Optional<Cart> cartOpt = this.cartRepository.findBymember(member);
		Cart cart = null;
		if (cartOpt.isPresent()) {
			cart = cartOpt.get();
		} else {
			throw new IllegalArgumentException("장바구니 없음");
		}
		// cart를 통해 cartList 를 찾아야함
		List<CartItem> cartItems = this.cartItemRepostiroy.findByCart(cart);
		return cartItems;
	}

	// principal 을 통해서 주문 목록 dto 불러오기
	public List<BookOrderResponseDto> getOrderList(String id) {
		List<CartItem> cartItems = getCartItem(id);
		return cartItems.stream().map(item -> {
			Detail detail = item.getDetail();
			return new BookOrderResponseDto(detail.getBookName(), detail.getThumbnail(), item.getCount(),
					detail.getPrice());
		}).collect(Collectors.toList());
	}

	// 카트의 주문목록의 총 가격 구하기
	public Integer getOrderTotalPrice(String id) {
		List<CartItem> cartItems = getCartItem(id);
		int totalPrice = cartItems.stream().mapToInt(item -> item.getCount() * item.getDetail().getPrice()).sum();
		return totalPrice;
	}

	// 주문목록을 저장하는 메서드
	@Transactional
	public int orderInsert(String id, int totalPrice, String request) {
		Optional<Member> memberOpt = this.memberService.findById(id);
		Member member = null;
		
		if (memberOpt.isPresent()) {
			member = memberOpt.get();
		} else {
			throw new IllegalArgumentException("해당된 멤버 없음");
		}

		Optional<Cart> cartOpt = this.cartRepository.findBymember(member); // 멤버를 통해 지금 장바구니 에있는 목록 가져오기
		Cart cart = null;
		if (cartOpt.isPresent()) {
			cart = cartOpt.get();
		}

		// 지금 bookOrder 을 만들어야되는거아니낙
		BookOrder bookOrder = new BookOrder();
		bookOrder.setMember(member);
		bookOrder.setOrderDate(LocalDateTime.now());
		bookOrder.setRequest(request);
		bookOrder.setTotalPrice(totalPrice);

		for (CartItem cartItem : cart.getItems()) {
			BookOrderDetail detail = new BookOrderDetail();
			detail.setBookOrder(bookOrder);
			detail.setBookName(cartItem.getDetail().getBookName());
			detail.setCount(cartItem.getCount());
			detail.setPrice(cartItem.getDetail().getPrice());
			detail.setThumbnail(cartItem.getDetail().getThumbnail());

			bookOrder.getBookOrderDetailList().add(detail); // 중요 !!
		}

		// 장바구니에 저장하고 카트 비우기
		cart.getItems().clear();
		this.cartRepository.save(cart);
		this.bookOrderRepository.save(bookOrder);

		return bookOrder.getOrderIdx();
	}

	// 바로 구매 메서드
	public int puyNow(String id, int totalPrice, String request, String isbn) {
		Optional<Member> memberOpt = this.memberService.findById(id);
		Member member = null;
		if (memberOpt.isPresent()) {
			member = memberOpt.get();
		} else {
			throw new IllegalArgumentException("해당된 멤버 없음");
		}

		// 지금 bookOrder 을 만들어야되는거아니낙
		BookOrder bookOrder = new BookOrder();
		bookOrder.setMember(member);
		bookOrder.setOrderDate(LocalDateTime.now());
		bookOrder.setRequest(request);
		bookOrder.setTotalPrice(totalPrice);
		
		//isbn 으로 bookOrderDetail 저장해야함 
		Detail detail = this.detailRepository.findByisbn(isbn);
		BookOrderDetail bookOrderdetail = new BookOrderDetail();
		bookOrderdetail.setBookOrder(bookOrder);
		bookOrderdetail.setBookName(detail.getBookName());
		bookOrderdetail.setCount(1);
		bookOrderdetail.setPrice(detail.getPrice());
		bookOrderdetail.setThumbnail(detail.getThumbnail());

		bookOrder.getBookOrderDetailList().add(bookOrderdetail); // 중요 !!
		
		this.bookOrderRepository.save(bookOrder);
		return bookOrder.getOrderIdx();
	}
	
	//bookOrderIdx -> 주문목록 조회 
	public List<BookOrderResponseDto> getBookOrder(int idx) {
		BookOrder bookOrder = null;
		Optional<BookOrder> bookOrderOpt = this.bookOrderRepository.findByOrderIdx(idx);
		if (bookOrderOpt.isPresent()) {
			bookOrder = bookOrderOpt.get();
		} else {
			throw new IllegalArgumentException("해당 주문이 없습니다.");
		}

		List<BookOrderResponseDto> dtoList = bookOrder.getBookOrderDetailList().stream().map(detail -> {
			BookOrderResponseDto dto = new BookOrderResponseDto();
			dto.setBookName(detail.getBookName());
			dto.setCount(detail.getCount());
			dto.setPrice(detail.getPrice());
			dto.setThumbnail(detail.getThumbnail());
			// dtoList.add(dto);
			return dto;

		}).collect(Collectors.toList());

		return dtoList;
	}

	// bookOrderIdx -> 전체 주문가격
	public int getOrderCount(int bookOrder_idx) {
		BookOrder bookOrder = this.bookOrderRepository.findByOrderIdx(bookOrder_idx)
				.orElseThrow(() -> new IllegalArgumentException("해당 bookOrderIdx이 없습니다."));
		return bookOrder.getTotalPrice();
	}

	// boodOrderIdx -> 전체 주문 권수
	public int getOrderTotalCount(int bookOrder_idx) {
		BookOrder bookOrder = this.bookOrderRepository.findByOrderIdx(bookOrder_idx)
				.orElseThrow(() -> new IllegalArgumentException("해당 bookOrderIdx이 없습니다."));

		List<BookOrderDetail> detailList = bookOrder.getBookOrderDetailList();
		int totalCount = detailList.stream().mapToInt(BookOrderDetail::getCount).sum();
		return totalCount;
	}

	// member.id -> List<BookOrderListDto> 조회 -> 복습
	public List<BookOrderListDto> getBookOrderListDto(String id) {
		Member member = this.memberService.findMemberById(id);
		List<BookOrder> bookOrderList = this.bookOrderRepository.findBymember(member);
		List<BookOrderListDto> dtoList = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

		for (BookOrder order : bookOrderList) {
			Integer totalPrice = order.getTotalPrice();
			String orderDate = order.getOrderDate().format(formatter);
			
			List<BookOrderDetail> detailList = order.getBookOrderDetailList();

			// detailList가 비어있으면 skip
			if (detailList.isEmpty()) {
				continue;
			}

			// 주문 하나당 총 수량 계산
			int totalCount = order.getBookOrderDetailList().stream().mapToInt(BookOrderDetail::getCount).sum();
			
			// 대표 책 정보 (첫 번째 책)
			BookOrderDetail firstDetail = order.getBookOrderDetailList().get(0);
			String bookName = firstDetail.getBookName();
			String thumbnail = firstDetail.getThumbnail();

			BookOrderListDto dto = new BookOrderListDto(totalPrice, orderDate, bookName, thumbnail, totalCount);
			dtoList.add(dto);
		}
		return dtoList;
	}
}