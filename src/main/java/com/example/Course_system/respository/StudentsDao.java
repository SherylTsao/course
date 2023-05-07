package com.example.Course_system.respository;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.example.Course_system.entity.Students;

@Repository
public interface StudentsDao extends JpaRepository<Students, Integer>{

	
}
