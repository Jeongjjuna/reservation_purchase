package com.example.newsfeed_service.article.application;

import com.example.newsfeed_service.article.application.port.ArticleRepository;
import com.example.newsfeed_service.article.domain.Article;
import com.example.newsfeed_service.article.domain.ArticleCreate;
import com.example.newsfeed_service.member.application.port.MemberRepository;
import com.example.newsfeed_service.member.exception.MemberErrorCode;
import com.example.newsfeed_service.member.exception.MemberException.MemberNotFoundException;
import com.example.newsfeed_service.member.domain.Member;
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