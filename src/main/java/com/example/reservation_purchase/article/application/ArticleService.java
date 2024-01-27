package com.example.reservation_purchase.article.application;

import com.example.reservation_purchase.article.application.port.ArticleRepository;
import com.example.reservation_purchase.article.domain.Article;
import com.example.reservation_purchase.article.domain.ArticleCreate;
import com.example.reservation_purchase.member.application.port.MemberRepository;
import com.example.reservation_purchase.member.domain.Member;
import com.example.reservation_purchase.member.exception.MemberErrorCode;
import com.example.reservation_purchase.member.exception.MemberException.MemberNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    public ArticleService(final ArticleRepository articleRepository, final MemberRepository memberRepository) {
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
