package jsh.project.board.article.enums;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ImportanceEnumMapper {
	
	public ImportanceEnumMapper() {
		
	}
	
	public List<ImportanceDto> getImportanceList() {
		List<ImportanceDto> list = new ArrayList<ImportanceDto>();
		for(ArticleImportance importance : ArticleImportance.values()) {
			list.add(new ImportanceDto(importance));
		}
		return list;
	}

}
