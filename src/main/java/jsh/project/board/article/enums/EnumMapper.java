package jsh.project.board.article.enums;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class EnumMapper {
	
	public EnumMapper() {
		
	}
	
	public String getCategory(String category) {
		return AllCategory.valueOf(category.toUpperCase()).getCategory();
	}
	
	public List<CategoryDto> getAdminCategory() {
		List<CategoryDto> list = new ArrayList<CategoryDto>();
		for(AdminCategory adminCategorys : AdminCategory.values()) {
			list.add(new CategoryDto(adminCategorys));
		}
		return list;
	}
	
	public List<CategoryDto> getUserCategory(){
		List<CategoryDto> list = new ArrayList<CategoryDto>();
		for(UserCategory userCategorys : UserCategory.values()) {
			list.add(new CategoryDto(userCategorys));
		}
		return list;
	}
	

}
