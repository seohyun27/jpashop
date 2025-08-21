package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)     // 기본을 읽기 모드로 설정 (DB 조회 이외에 삽입/삭제 시 false 옵션 필수!)
@RequiredArgsConstructor            // final을 포함한 생성자 자동 생성
public class ItemService {

    private final ItemRepository itemRepository;

    /// 서비스 클래스의 서비스 메소드들
    /// 해당 경우는 리파지토리 클래스의 메소드들을 그대로 사용하게 됨
    /// 이런 경우 지금처럼 서비스 클래스를 따로 만들 필요 없이, 컨트롤러에서 리파지토리 클래스를 바로 가져와서 사용해도 문제 없음!!
    /// 설계 원칙을 지키는 것만큼이나 유연한 설계를 하는 것이 중요함

    @Transactional      // readOnly를 false로 처리하기 위해 필요함
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    public List<Item> findItem(){
        return itemRepository.findAll();
    }

    public Item findOne(Long id){
        return itemRepository.findOne(id);
    }
}
