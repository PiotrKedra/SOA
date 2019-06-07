package helloG.dao;

import helloG.jpa.ProjectX;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class ProjectxDAO implements xDAO<ProjectX> {

    @PersistenceContext(unitName = "primary")
    EntityManager entityManager;
    @Resource
    UserTransaction utx;

    @Override
    public Optional<ProjectX> get(int id) {
        return Optional.ofNullable(entityManager.find(ProjectX.class, id));
    }

    @Override
    public List<ProjectX> getAll() {
        Query query = entityManager.createQuery("select p from ProjectX p");
        return query.getResultList();
    }

    @Override
    public void save(ProjectX projectX) {
        executeInsideTransaction(em -> em.persist(projectX));
    }

    @Override
    public void update(ProjectX projectX) {
        executeInsideTransaction(entityManager -> entityManager.merge(projectX));
    }

    @Override
    public void delete(ProjectX projectX) {

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
