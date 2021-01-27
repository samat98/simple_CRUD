package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    private LocalContainerEntityManagerFactoryBean entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction transaction;

    @Autowired
    public void setEntityManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    private void prepareEntityManager() {
        entityManager = entityManagerFactory
                .getNativeEntityManagerFactory()
                .createEntityManager();
        transaction = entityManager.getTransaction();
        transaction.begin();
    }

    @Override
    public void saveUser(User user) {
        prepareEntityManager();
        try {
            entityManager.persist(user);
        } catch (Exception e) {
            transaction.rollback();
        }
        transaction.commit();
    }

    @Override
    public void updateUser(User user) {
        prepareEntityManager();
        User user1 = entityManager.find(User.class, user.getId());
        user1.setName(user.getName());
        user1.setLastName(user.getLastName());
        transaction.commit();
    }

    @Override
    public User findUser(Long id) {
        prepareEntityManager();
        User user = entityManager.find(User.class, new Long(id));
        entityManager.detach(user);
        transaction.commit();
        return user;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        entityManager = entityManagerFactory
                .getNativeEntityManagerFactory()
                .createEntityManager();
        return (List<User>) entityManager.createQuery("SELECT u FROM User u").getResultList();
    }

    @Override
    public void deleteUser(Long id) {
        prepareEntityManager();
        User user = entityManager.find(User.class, new Long(id));
        entityManager.remove(user);
        transaction.commit();
    }
}
