package com.popup.dessert.dessertpopup.Service;

import com.popup.dessert.dessertpopup.Dto.UserRequest;
import com.popup.dessert.dessertpopup.Dto.UserResponse;

public interface UserService {
  UserResponse signUp(UserRequest userRequest);
}
