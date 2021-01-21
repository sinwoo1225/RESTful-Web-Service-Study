package com.inflearn.restfull.user;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class UserDaoService {
    private static final List<User> users = new ArrayList<>();

    private static int usersCount = 3;

    static {
        users.add(new User(1, "Shinwoo", new Date(), "1234", "111111-1111111"));
        users.add(new User(2, "Alice", new Date(), "1234", "111111-1111111"));
        users.add(new User(3, "Egoing", new Date(), "1234", "111111-1111111"));
    }

    public List<User> findAll() {
        return users;
    }

    public User save(User user) {
        if(user.getId() == null){
            user.setId(++usersCount);
        }

        users.add(user);
        return user;
    }

    public User findOneBy(int id) {
        for(User user : users) {
            if(user.getId() == id){
                return user;
            }
        }
        return null;
    }

    public User deleteBy(int id) {
        Iterator<User> iterator = users.iterator();

        while(iterator.hasNext()){
            User user = iterator.next();
            if(user.getId() == id) {
                iterator.remove();
                return user;
            }
        }
        return null;
    }
}
