package org.cisiondata.modules.auth.service.converter;

import org.cisiondata.modules.abstr.service.converter.ConverterAbstrImpl;
import org.cisiondata.modules.abstr.service.converter.IConverter;
import org.cisiondata.modules.auth.dto.UserDTO;
import org.cisiondata.modules.auth.entity.AuthUser;

public class UserConverter extends ConverterAbstrImpl<AuthUser, UserDTO> {
	
	private UserConverter() {}

	private static class UserConverterHolder{
		private static UserConverter instance = new UserConverter();
	}

	public static IConverter<AuthUser, UserDTO> getInstance() {
		return UserConverterHolder.instance;
	}
	
	@Override
	public void convertEntity2DTO(AuthUser user, UserDTO userDTO) {
		convert(user, userDTO);
	}
	
	@Override
	public void convertDTO2Entity(UserDTO userDTO, AuthUser user) {
		convert(userDTO, user);
	}
	
}
