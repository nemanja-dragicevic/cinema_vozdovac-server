//package com.master.BioskopVozdovac.repository;
//
//import com.master.BioskopVozdovac.member.model.MemberEntity;
//import com.master.BioskopVozdovac.member.repository.MemberRepository;
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import static com.master.BioskopVozdovac.input.MemberData.*;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
////@ExtendWith(SpringExtension.class)
//@DataJpaTest
//public class MemberRepositoryTest implements CRUDRepositoryTest {
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Override
//    @Test
//    public void testSave() {
//        MemberEntity entity = memberRepository.save(MEMBER_ENTITY);
//
//        assertEquals(MEMBER_ENTITY.getFirstName(), entity.getFirstName());
//    }
//
//    @Override
//    public void testUpdate() {
//
//    }
//
//    @Override
//    public void testFind() {
//
//    }
//
//    @Override
//    public void testDelete() {
//
//    }
//
//}
