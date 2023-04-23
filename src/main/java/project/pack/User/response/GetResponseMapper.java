package project.pack.User.response;

import org.springframework.stereotype.Service;
import project.pack.User.model.UserModel;

import java.util.function.Function;

@Service
public class GetResponseMapper implements Function<UserModel, GetResponse> {
    @Override
    public GetResponse apply(UserModel userModel) {
        return new GetResponse(
                userModel.getId(),
                userModel.getUsername(),
                userModel.getRole()
        );
    }
}