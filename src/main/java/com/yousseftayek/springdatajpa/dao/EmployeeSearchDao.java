package com.yousseftayek.springdatajpa.dao;

import com.yousseftayek.springdatajpa.models.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EmployeeSearchDao {

    private final EntityManager em;
    public List<Employee> findAllBySimpleQuery(
            String firstname,
            String lastname,
            String email
    ){
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        // select * from employee
        Root<Employee> root = criteriaQuery.from(Employee.class);

        // prepare WHERE clause
        // WHERE firstname like '%ali%'
        Predicate firstnamePredicate = criteriaBuilder
                .like(root.get("firstname"), "%" + firstname + "%");

        Predicate lastnamePredicate = criteriaBuilder
                .like(root.get("lastname"), "%" + lastname + "%");

        Predicate emailPredicate = criteriaBuilder
                .like(root.get("email"), "%" + email + "%");

        Predicate firstname0rLastnamePredicate = criteriaBuilder.or(
                firstnamePredicate,
                lastnamePredicate,
                emailPredicate
        );
        // => final query ==> select * from employee where firstname like '%ali%'
        // or lastname like '%ali%' and email like '%ali%'
        var andEmailPredicate = criteriaBuilder.and(firstname0rLastnamePredicate, emailPredicate);
        criteriaQuery.where(andEmailPredicate);
        TypedQuery<Employee> query = em.createQuery(criteriaQuery);

        return query.getResultList();
    }

    public List<Employee> findAllByCriteria(
            SearchRequest request
    ){
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        List<Predicate> predicates = new ArrayList<>();

        // select from employee
        Root<Employee> root = criteriaQuery.from(Employee.class);
        if (request.getFirstName() != null) {
            Predicate firstnamePredicate = criteriaBuilder
                    .like(root.get("firstname"), "%" + request.getFirstName() + "%");

        }
        if (request.getLastName() != null) {
            Predicate firstnamePredicate = criteriaBuilder
                    .like(root.get("lastname"), "%" + request.getLastName() + "%");

        }
        if (request.getEmail() != null) {
            Predicate firstnamePredicate = criteriaBuilder
                    .like(root.get("email"), "%" + request.getEmail() + "%");
        }

        criteriaQuery.where(
                criteriaBuilder.or(predicates.toArray(new Predicate[0]))
        );
        TypedQuery<Employee> query = em.createQuery(criteriaQuery);
        return query.getResultList();

    }
}
