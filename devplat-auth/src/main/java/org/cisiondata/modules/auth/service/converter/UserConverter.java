package org.cisiondata.modules.auth.service.converter;

import org.cisiondata.modules.abstr.service.converter.ConverterAbstrImpl;
import org.cisiondata.modules.abstr.service.converter.IConverter;
import org.cisiondata.modules.auth.dto.UserDTO;
import org.cisiondata.modules.auth.entity.User;

public class UserConverter extends ConverterAbstrImpl<User, UserDTO> {
	
	private UserConverter() {}

	private static class UserConverterHolder{
		private static UserConverter instance = new UserConverter();
	}

	public static IConverter<User, UserDTO> getInstance() {
		return UserConverterHolder.instance;
	}
	
	@Override
	public void convertEntity2DTO(User user, UserDTO userDTO) {
		convert(user, userDTO);
	}
	
	@Override
	public void convertDTO2Entity(UserDTO userDTO, User user) {
		convert(userDTO, user);
	}
	
}
