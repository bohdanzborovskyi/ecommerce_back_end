package com.zbodya.ecomerce_back.repository;

import com.zbodya.ecomerce_back.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

  public Category findByName(String name);

  @Query(
      "SELECT c FROM Category c WHERE c.name=:name AND c.parentCategory.name=:parentCategoryName")
  public Category findByNameAndParentCategory(
      @Param("name") String name, @Param("parentCategoryName") String parentCategoryName);
}
