package com.example.recruit.repository;

import com.example.recruit.entity.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * File: RecruitRepository.java
 * Description: RecruitRepository 데이터베이스 접근 인터페이스
 */

@Repository
public interface RecruitRepository extends JpaRepository<Recruit, Long> {

}
