package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    private LocalContainerEntityManagerFactoryBean entityManagerFactory;
    private EntityManager entityManager;

    @Autowired
    public void setEntityManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    private void prepareEntityManager() {
        entityManager = entityManagerFactory
                .getNativeEntityManagerFactory()
                .createEntityManager();
    }

    @Override
    public void saveUser(User user) {
        prepareEntityManager();
        entityManager.persist(user);
        entityManager.close();
    }

    @Override
    public void updateUser(User user) {
        prepareEntityManager();
        User user1 = entityManager.find(User.class, user.getId());
        user1.setName(user.getName());
        user1.setLastName(user.getLastName());
        entityManager.close();
    }

    @Override
    public User findUser(Long id) {
        prepareEntityManager();
        User user = entityManager.find(User.class, id);
        entityManager.detach(user);
        entityManager.close();
        return user;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        prepareEntityManager();
        List<User> users = (List<User>) entityManager.createQuery("SELECT u FROM User u").getResultList();
        entityManager.close();
        return users;
    }

    @Override
    public void deleteUser(Long id) {
        prepareEntityManager();
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
        entityManager.close();
    }
}
