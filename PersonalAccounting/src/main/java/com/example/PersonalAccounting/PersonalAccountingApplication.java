package com.example.PersonalAccounting;

import com.example.PersonalAccounting.services.finantial_arrangement_calculations.FinancialArrangementStartEndTransactionCreator;
import com.example.PersonalAccounting.services.finantial_arrangement_calculations.impl.CreditCalculations;
import com.example.PersonalAccounting.services.finantial_arrangement_calculations.impl.CreditStartEndTransactionCreator;
import com.example.PersonalAccounting.services.finantial_arrangement_calculations.impl.DepositCalculations;
import com.example.PersonalAccounting.services.finantial_arrangement_calculations.FinancialArrangementCalculations;
import com.example.PersonalAccounting.services.finantial_arrangement_calculations.impl.DepositStartEndTransactionCreator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
public class PersonalAccountingApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalAccountingApplication.class, args);
	}

	@Bean
	@Autowired
	public List<FinancialArrangementCalculations> financialArrangementCalculationsList(CreditCalculations credit,
																					   DepositCalculations deposit) {
		return List.of(credit, deposit);
	}

	@Bean
	@Autowired
	public List<FinancialArrangementStartEndTransactionCreator> financialArrangementTransactionCreatorList(CreditStartEndTransactionCreator credit,
																										   DepositStartEndTransactionCreator deposit) {
		return List.of(credit, deposit);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
