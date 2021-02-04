package com.jcustom.filter;

import java.util.Arrays;

/**
 * 
 * @author ZAKARIA
 * 
 * Generate Custom Filter Using JAVA or Get JSON Object
 *
 */
public class Application {

	public static void main(String[] args) {
		
		ServiceFilter filter = new ServiceFilter();
		
		/**
		 * Example : 
		 */
		
		ServiceFilterElement serviceFilterElement = new ServiceFilterElement();
		serviceFilterElement.setField("departement.nom");
		serviceFilterElement.setAlias("pd");
		serviceFilterElement.setValue(Arrays.asList(new String[]{"Dep1", "Dep2" , "Dep3"}));
		serviceFilterElement.setCondition("inset");
		
		ServiceFilterElement serviceFilterElement1 = new ServiceFilterElement();
		serviceFilterElement1.setField("categorie.num");
		serviceFilterElement1.setAlias("ca");
		serviceFilterElement1.setValue(Arrays.asList(new String[]{"1"}));
		serviceFilterElement1.setCondition("gt");
		
		ServiceFilterElement serviceFilterElement3 = new ServiceFilterElement();
		serviceFilterElement3.setField("num");
		serviceFilterElement3.setAlias("lg");
		serviceFilterElement3.setValue(Arrays.asList(new String[]{"3"}));
		serviceFilterElement3.setCondition("eq");
		
		ServiceFilterElement serviceFilterElement4 = new ServiceFilterElement();
		serviceFilterElement4.setField("name");
		serviceFilterElement4.setAlias("lg");
		serviceFilterElement4.setValue(Arrays.asList(new String[]{"%test%"}));
		serviceFilterElement4.setCondition("like");
		
		// first filter group dosn't need operator (AND by default)
		ServiceFilterGroup serviceFilterGroup = new ServiceFilterGroup();
		serviceFilterGroup.setFilters(Arrays.asList(serviceFilterElement , serviceFilterElement3));
				
		ServiceFilterGroup serviceFilterGroup1 = new ServiceFilterGroup();
		serviceFilterGroup1.setFilters(Arrays.asList(serviceFilterElement1 , serviceFilterElement4));
		serviceFilterGroup1.setOperator("OR");
		
		filter.setFiltersGroupes(Arrays.asList(serviceFilterGroup , serviceFilterGroup1));
		
		
		String filterQuery = generateFilterQuery(filter);
		
		/**
		 * il faut connait les tables quand va recupere les donnes + donnes des alias a ces jointeurs
		 *  pour l'utilise dans l'objet ServiceFilterElement (fields,alias).
		 */
		String query = "SELECT  c FROM Collaborateur c "
				+ "LEFT JOIN c.categorie ca "
				+ "LEFT JOIN c.ligneGroupes lg "
				+ "LEFT JOIN c.periodeDepartements pd "
				+ "where 1 = 1 " + filterQuery.toString();
		
		System.out.println("Query => " + query);

	}
	
	public static String generateFilterQuery(ServiceFilter filter) {
		StringBuilder filterQuery = new StringBuilder();
		filter.getFiltersGroupes().forEach(filterGroupe -> {
			
			if(filter.getFiltersGroupes().size() > 1) {
				filterQuery.append(filterGroupe.getOperator()  + " (");
			}
			filterGroupe.getFilters().forEach(filters -> {
				
				int indexFilterGroup = 0; // for multiple filter group append AND is not append empty string
				
				
				String operation = "";

				switch (filters.getCondition()) {
				case "inset":
					operation = " IN ";
					break;
				case "ninset":
					operation = " NOT IN ";
					break;
				case "eq":
					operation = " = ";
					break;
				case "neq":
					operation = " != ";
					break;
				case "gt":
					operation = " > ";
					break;
				case "lt":
					operation = " < ";
					break;
				case "gte":
					operation = " >= ";
					break;
				case "lte":
					operation = " <= ";
					break;
				case "like":
					operation = " like ";
					break;
				default:
					break;
				}
				if (operation == " IN " || operation == " NOT IN ") {
					
					int indexFilterElement = 0;// for multiple filter element append AND is not append empty string
					
					String value = filters.getValue().toString().replaceAll("[\\[\\]]", "");
					String[] values = value.split(",");
					filterQuery.append((indexFilterGroup > 1 ? " AND " : " ") + filters.getAlias()+ ".").append(filters.getField())
					.append(operation + "(");
							
					for(int i = 0 ; i < values.length ; i++) {
						indexFilterElement++;
						filterQuery.append("'"+values[i].trim()+"'");
						if(i != values.length - 1)
							filterQuery.append(",");
					}
					filterQuery.append(")" + (indexFilterElement > 1 ? " AND " : " "));
					
					
				}else if(Arrays.asList(new String[] { " = "  , " > " , " < " , " >= " , " =< " ," like "}).contains(operation) ){
					
					String value = filters.getValue().toString().replaceAll("[\\[\\]]", "");
					filterQuery.append((indexFilterGroup > 1 ? " AND " : " ") + filters.getAlias()+ ".").append(filters.getField())
					.append(operation);
					filterQuery.append("'"+value+"'");
					
					// when we have multiple filter element in group by with simple condition( = , > , ...)
					if(filterGroupe.getFilters().indexOf(filters) != filterGroupe.getFilters().size() - 1) {
						filterQuery.append(" AND ");
					}
					
				}
				indexFilterGroup++;
			});
			
			filterQuery.append(") ");
		});
		
		return filterQuery.toString();
	}

}
