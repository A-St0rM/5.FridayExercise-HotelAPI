package app.security;

import app.DAO.SecurityDAO;
import app.DTO.UserDTO;
import app.config.HibernateConfig;
import app.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.Set;


public class SecurityController implements ISecurityController{

    ISecurityDAO securityDAO = new SecurityDAO(HibernateConfig.getEntityManagerFactory());
    ObjectMapper objectMapper = new Utils().getObjectMapper();

    @Override
    public Handler login() {
        return (Context ctx)->{
            User user = ctx.bodyAsClass(User.class);
            User checkedUser = securityDAO.getVerifiedUser(user.getUsername(), user.getPassword());
            System.out.println("Succes for user: " + checkedUser.getUsername());
            ObjectNode on = objectMapper.createObjectNode().put("msg", "Login was succesfull");
            ctx.json(on).status(200);
        };
    }

    @Override
    public Handler register() {
        return null;
    }

    @Override
    public Handler authenticate() {
        return null;
    }

    @Override
    public boolean authorize(UserDTO userDTO, Set<String> allowedRoles) {
        return false;
    }

    @Override
    public String createToken(UserDTO user) throws Exception {
        return "";
    }

    @Override
    public UserDTO verifyToken(String token) throws Exception {
        return null;
    }
}
