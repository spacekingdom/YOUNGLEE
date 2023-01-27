package com.uni.practice.member.service;

import com.uni.practice.member.dao.MemberMapper;
import com.uni.practice.member.dto.MemberDto;
import com.uni.practice.product.dto.ProductDto;
import com.uni.practice.util.FileUploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
public class MemberService {
    private final MemberMapper memberMapper;

    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberMapper memberMapper, PasswordEncoder passwordEncoder) {
        this.memberMapper = memberMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public MemberDto selectMyInfo(@PathVariable String memberId) {
        log.info("[MemberService] getMyInfo Start ==============================");

        MemberDto member = memberMapper.selectByMemberId(memberId);
        log.info("[MemberService] {}", member);
        log.info("[MemberService] getMyInfo End ==============================");

        return member;
    }

    @Transactional
    public int updateMeber(MemberDto memberDto) {
        memberDto.setMemberPassword(passwordEncoder.encode(memberDto.getMemberPassword()));
        return memberMapper.updateMember(memberDto);
    }

    public int deleteMember(String memberId) {
        return memberMapper.deleteMember(memberId);
    }
}