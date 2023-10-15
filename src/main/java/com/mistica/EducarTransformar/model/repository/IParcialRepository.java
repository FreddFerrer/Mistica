package com.mistica.EducarTransformar.model.repository;

import com.mistica.EducarTransformar.model.entity.Parcial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IParcialRepository extends JpaRepository<Parcial, Long> {

}
