package com.example.user_service.article.application;

import com.example.user_service.article.application.port.ArticleRepository;
import com.example.user_service.article.domain.Article;
import com.example.user_service.article.domain.ArticleCreate;
import com.example.user_service.member.application.port.MemberRepository;
import com.example.user_service.member.domain.Member;
import com.example.user_service.member.exception.MemberErrorCode;
import com.example.user_service.member.exception.MemberException.MemberNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    public ArticleService(
            final ArticleRepository articleRepository,
            final MemberRepository memberRepository
    ) {
        this.articleRepository = articleRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Long create(Long principalId, ArticleCreate articleCreate) {

        Member member = findExistMember(principalId);

        Article article = Article.create(member.getId(), articleCreate);

        return articleRepository.save(article).getId();
    }

    private Member findExistMember(Long id) {
        return memberRepository.findById(id).orElseThrow(() ->
                new MemberNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

}
