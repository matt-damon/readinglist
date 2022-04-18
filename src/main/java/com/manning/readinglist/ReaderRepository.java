package com.manning.readinglist;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 自动配置
 * Classpath里有H2，会创建一个嵌入式的H2数据库Bean，它的类型是javax.sql.DataSource，JPA实现(Hibernate)需要它来访问数据库
 * Classpath里有Hibernate(Spring Data JPA传递引入的)的实体管理器，所以自动配置会配置与Hibernate相关的Bean
 * Classpath里有Spring Data JPA，所以它会自动配置为根据仓库的接口创建仓库实现
 */
//spring data jpa(jpa抽象) -> hibernate(jpa实现) -> h2(数据库)
public interface ReaderRepository extends JpaRepository<Reader, String> {

}
