package helloG.dao;

import helloG.jpa.StudentX;

import javax.annotation.Resource;
import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.transaction.*;
import javax.transaction.RollbackException;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class StudentxDAO implements xDAO<StudentX> {

    @PersistenceContext(unitName = "primary")
    EntityManager entityManager;
    @Resource
    UserTransaction utx;


    public StudentxDAO() {
    }

    // select
    public List<StudentX> search(StudentX studentX){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<StudentX> query = cb.createQuery(StudentX.class);

        Root<StudentX> s = query.from(StudentX.class);

        String surname = studentX.getSurname();
        if(surname==null) surname = "%";

        String name = studentX.getName();
        if(name==null) name = "%";

        String email = studentX.getEmail();
        if(email==null) email="%";

        query.select(s)
                .where(
                        cb.and(
                                cb.like(s.get("surname"), surname),
                                cb.like(s.get("name"), name),
                                cb.like(s.get("email"), email)));



        TypedQuery<StudentX> r = entityManager.createQuery(query);

        System.out.println(r.unwrap(org.hibernate.Query.class).getQueryString());

        return r.getResultList();
    }


    @Override
    public Optional<StudentX> get(int id) {
        return Optional.ofNullable(entityManager.find(StudentX.class, id));
    }

    @Override
    public List<StudentX> getAll() {
        Query query = entityManager.createQuery("select s from StudentX s");
        return query.getResultList();
    }

    @Override
    public void save(StudentX student) {
        executeInsideTransaction(entityManager -> entityManager.persist(student));
    }

    @Override
    public void update(StudentX student) {
        executeInsideTransaction(entityManager -> entityManager.merge(student));
    }

    @Override
    public void delete(StudentX student) {
        executeInsideTransaction(entityManager -> entityManager.remove(entityManager.contains(student) ? student : entityManager.merge(student)));
    }



    private void executeInsideTransaction(Consumer<EntityManager> action) {
        try {
            utx.begin();
            action.accept(entityManager);
            utx.commit();
        }
        catch (RuntimeException e) {
            try {
                utx.rollback();
            } catch (SystemException e1) {
                e1.printStackTrace();
            }
            throw e;
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
            e.printStackTrace();
        }
    }
}

