package hdt.domain.forms;

import hdt.domain.modelcache.GenericModelCache;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.ui.Model;

public class LoginForm extends WebForm {
    @NotEmpty
    @Size(min = 1, max = 50)
    private String userName;
    @NotEmpty
    @Size(min = 1, max = 20)
    private String password;

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public String getUsername() {
        return userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
   
    @Override
    public void populateGenericModelWithFormData(GenericModelCache model) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void populateSpringModelWithFormData(Model model) {
        throw new UnsupportedOperationException("Not implemented.");
    }
}
