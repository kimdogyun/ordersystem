package com.example.commerce.ordering.repository;

import com.example.commerce.ordering.domain.Ordering;
import org.aspectj.weaver.Lint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Repository
public interface OrderingRepository extends JpaRepository <Ordering, Long>{
    List<Ordering> findByMemberEmail(String email);
}
