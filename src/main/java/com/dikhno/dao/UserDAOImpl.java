package com.dikhno.dao;

import com.dikhno.model.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    private static final int USER_ON_PAGE = 10;

    @Autowired
    private SessionFactory sessionFactory;
    private Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void addUser(User user) {
        getCurrentSession().save(user);
    }

    @Override
    public void updateUser(User user) {
        User userToUpdate = getUser(user.getId());
        userToUpdate.setName(user.getName());
        userToUpdate.setAge(user.getAge());
        userToUpdate.setIsAdmin(user.getIsAdmin());
        getCurrentSession().update(userToUpdate);
    }

    @Override
    public User getUser(int id) {
        User user = (User) getCurrentSession().get(User.class, id);
        return user;
    }

    @Override
    public void deleteUser(int id) {
        User user = getUser(id);
        if (user != null)
            getCurrentSession().delete(user);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getUsers(int page, String name) {
        Criteria criteria = getCurrentSession().createCriteria(User.class).setMaxResults(USER_ON_PAGE).setFirstResult(page * USER_ON_PAGE);
        if (name!="")
            criteria.add(Restrictions.like("name", (name + "%")));
        return criteria.list();
    }

    @Override
    public Integer getNumber(String name) {
        Criteria criteria = getCurrentSession().createCriteria(User.class);
        if (name!="")
            criteria.add(Restrictions.like("name", (name+"%")));
        int size = criteria.list().size();
        if (size%USER_ON_PAGE == 0) return size/USER_ON_PAGE;
        return size/USER_ON_PAGE + 1;
    }

}
