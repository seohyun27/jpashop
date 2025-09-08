package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model){
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("items/new")
    public String create(BookForm form){

        // static 생성 메소드를 만들어서 setter 없이 새 객체를 생성하는 게 가장 좋은 설계임
        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/items";   // 저장된 책 목록으로 리다이렉트
    }

    @GetMapping("/items")
    public String list(Model model){
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model){

        // 예제용 프로그램이므로 코드 단순화를 위해 Book으로 바로 캐스팅해서 사용함
        Book item = (Book) itemService.findOne(itemId);

        BookForm form = new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping("items/{itemId}/edit")
    public String updateItem(@ModelAttribute("form") BookForm form){

        // 해당 방법 사용 시 누군가가 id를 조작해 다른 사람의 정보를 건들일 수 있다
        // 서비스 계층에서 해당 유저가 수정 권한을 가진 유저인지 아닌지 걸러주는 로직이 필요

        // 더티 채킹
        // 서비스 계층으로 식별자와 변경할 데이터를 전달
        // 서비스 계층 내에서 DB 내부의 영속성 데이터를 가져와 더티 채킹 기능을 이용
        itemService.updateItem(form.getId(), form.getName(), form.getPrice(), form.getStockQuantity());

        return  "redirect:/items";

        // merge
        // JPA가 식벽할 수 있는 식별자가 DB 내에 존재 : 준영속 객체 (이미 해당 객체는 DB에 한 번 저장되었다)
        // 영속성 객체와 달리 JPA의 관리를 받지 봇한다

        /*
        Book book = new Book();
        book.setId(form.getId());
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return  "redirect:/items";
        */
    }
}
