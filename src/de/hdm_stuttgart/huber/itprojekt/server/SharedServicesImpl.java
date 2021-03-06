package de.hdm_stuttgart.huber.itprojekt.server;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import de.hdm_stuttgart.huber.itprojekt.server.db.UserInfoMapper;
import de.hdm_stuttgart.huber.itprojekt.shared.SharedServices;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;

import java.util.Vector;

/**
 * @see SharedServices
 */
public class SharedServicesImpl extends RemoteServiceServlet implements SharedServices {

    /**
     * Automatisch von eclipse generiert damit es Ruhe gibt
     */
    private static final long serialVersionUID = 1L;
    UserInfoMapper userInfoMapper = UserInfoMapper.getUserInfoMapper();

    @Override
    public void init() throws IllegalArgumentException {

    }

    @Override
    public UserInfo login(String requestUri) {

        UserService userService = UserServiceFactory.getUserService();
        UserInfo userInfo = new UserInfo();
        userInfoMapper = UserInfoMapper.getUserInfoMapper();

        if (userService.isUserLoggedIn()) {

            User user = userService.getCurrentUser();

            userInfo.setLoggedIn(true);
            userInfo.setEmailAddress(user.getEmail());
            userInfo.setNickname(user.getNickname());
            userInfo.setLogoutUrl(userService.createLogoutURL(requestUri));
            userInfo.setGoogleId(user.getUserId());

            // Registration aktuell noch etwas unzeremoniell
            if (!userInfoMapper.isUserRegistered(userInfo.getEmailAddress())) {

                userInfoMapper.registerUser(userInfo);

            }

            UserInfo someUser = userInfoMapper.findByEmailAdress(user.getEmail());
            userInfo.setFirstName(someUser.getFirstName());
            userInfo.setSurName(someUser.getSurName());
            userInfo.setId(someUser.getId());

            userInfo.setAdminStatus(userService.isUserAdmin());

        } else {

            userInfo.setLoggedIn(false);
            userInfo.setLoginUrl(userService.createLoginURL(requestUri));

        }

        return userInfo;
    }

    @Override
    public Vector<UserInfo> getAllUsers() {

        return userInfoMapper.getAllUserInfos();

    }

}
