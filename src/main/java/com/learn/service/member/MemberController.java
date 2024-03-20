package com.learn.service.member;

import com.learn.service.member.dto.MemberCommandDto;
import com.learn.service.member.dto.MemberRepresentationDto;
import com.learn.service.member.model.Member;
import com.learn.service.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("")
    public ResponseEntity<String> createANewMember(@RequestBody MemberCommandDto request, UriComponentsBuilder ucb) {
        Long id = memberService.createANewMember(request);
        URI locationOfNewMember = ucb.path("members/{requestId}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(locationOfNewMember).build();

    }


    @GetMapping("/{requestId}")
    public ResponseEntity<MemberRepresentationDto> getAMember(@PathVariable("requestId") Long requestId) {
        return ResponseEntity.ok(memberService.getAMemberById(requestId));
    }

    @GetMapping("")
    public ResponseEntity<List<Member>> getMembers() {
        return ResponseEntity.ok(memberService.getMembers());
    }
}
