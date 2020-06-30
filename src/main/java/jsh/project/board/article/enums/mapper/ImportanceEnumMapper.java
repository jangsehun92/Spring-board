package jsh.project.board.article.enums.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import jsh.project.board.article.enums.Importance;
import jsh.project.board.article.enums.dto.ImportanceDto;

@Component
public class ImportanceEnumMapper {
	
	public ImportanceEnumMapper() {
		
	}
	
	public List<ImportanceDto> getImportanceList() {
		List<ImportanceDto> list = new ArrayList<ImportanceDto>();
		for(Importance importance : Importance.values()) {
			list.add(new ImportanceDto(importance));
		}
		return list;
	}

}
