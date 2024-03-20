package com.learn.service.member.dto;

import com.learn.service.member.model.Member;
import com.learn.service.member.model.Organization;

 public record MemberRepresentationDto(Member member, Organization organization){
}
