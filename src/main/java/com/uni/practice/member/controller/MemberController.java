package com.uni.practice.member.controller;


import com.uni.practice.common.ResponseDto;
import com.uni.practice.member.dto.MemberDto;
import com.uni.practice.member.service.MemberService;
import com.uni.practice.product.dto.ProductDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

//  memberId로 회원정보 조회
    @GetMapping("/members/{memberId}")
    public ResponseEntity<ResponseDto> selectMyMemberInfo(@PathVariable String memberId) {

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", memberService.selectMyInfo(memberId)));
    }

//  회원정보 수정
    @PutMapping(value = "/members")
    public ResponseEntity<ResponseDto> updateMember(@ModelAttribute MemberDto memberDto) {
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "회원정보 업데이트 성공",  memberService.updateMeber(memberDto)));
    }

//  회원 탈퇴
    @DeleteMapping(value = "/members/{memberId}")
    public ResponseEntity<ResponseDto> deleteMember(@PathVariable String memberId){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "회원 탈퇴 성공",  memberService.deleteMember(memberId)));
    }

}
