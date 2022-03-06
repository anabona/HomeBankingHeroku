package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}


	@Bean
	public CommandLineRunner initData (ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
		return (args) -> {
			Client melba = new Client("Melba", "Morel", "melba@mindhub.com","melbita");
			melba.setPassword(passwordEncoder.encode(melba.getPassword()));
			clientRepository.save(melba);
			Client ana = new Client("Ana", "Rios", "arios@mindhub.com", "anuchi");
			ana.setPassword(passwordEncoder.encode(ana.getPassword()));
			clientRepository.save(ana);
			Client admin = new Client("admin", "admin", "admin@admin", "admin");
			admin.setPassword(passwordEncoder.encode(admin.getPassword()));
			clientRepository.save(admin);


			LocalDateTime today = LocalDateTime.now();
			LocalDateTime tomorrow = today.plusDays(1);

			Account vin001 = new Account("vin001", today, 496700.0, melba, true);
			Account vin002 = new Account("vin002", tomorrow, 1606500.0, melba, true);

			accountRepository.save(vin001);
			accountRepository.save(vin002);

			Account vin003 = new Account("vin003", today, 7000000.0, ana, true);
			Account vin004 = new Account("vin004", tomorrow, 30005000.0, ana, true);

			accountRepository.save(vin003);
			accountRepository.save(vin004);

			Transaction trans1 = new Transaction("trans1", tomorrow,TransactionType.DEBIT, 5000.0, "Shell Autoservicio", vin001);
			transactionRepository.save(trans1);
			Transaction trans2 = new Transaction("trans2", today,TransactionType.CREDIT, 200.0, "Transferencia", vin001);
			transactionRepository.save(trans2);
			Transaction trans3 = new Transaction("trans3", today.minusDays((long) 1),TransactionType.DEBIT, 3500.0, "Farmacity", vin001);
			transactionRepository.save(trans3);
			Transaction trans4 = new Transaction("trans4", today.minusDays((long) 2),TransactionType.DEBIT, 5000.0, "Transferencia", vin001);
			transactionRepository.save(trans4);
			Transaction trans5 = new Transaction("trans5", today.minusDays((long) 3),TransactionType.CREDIT, 500000.0, "Haberes", vin001);
			transactionRepository.save(trans5);

			Transaction trans6 = new Transaction("trans6", tomorrow,TransactionType.CREDIT, 50000.0, "Big Business", vin002);
			transactionRepository.save(trans6);
			Transaction trans7 = new Transaction("trans7", today.minusDays((long) 2),TransactionType.CREDIT, 20000.0, "Transferencia", vin002);
			transactionRepository.save(trans7);
			Transaction trans8 = new Transaction("trans8", today.minusDays((long) 3),TransactionType.DEBIT, 13500.0, "Day School", vin002);
			transactionRepository.save(trans8);
			Transaction trans9 = new Transaction("trans9", today.minusDays((long) 4),TransactionType.DEBIT, 50000.0, "Transferencia", vin002);
			transactionRepository.save(trans9);

			Transaction trans11 = new Transaction("trans11", tomorrow,TransactionType.DEBIT, 5000.0, "Beauty SPA", vin003);
			transactionRepository.save(trans11);
			Transaction trans12 = new Transaction("trans12", today,TransactionType.CREDIT, 200.0, "Transferencia", vin004);
			transactionRepository.save(trans12);
			Transaction trans13 = new Transaction("trans13", today.minusDays((long) 1),TransactionType.DEBIT, 3500.0, "Clinica Covid", vin004);
			transactionRepository.save(trans13);

			Loan hipo = new Loan("Hipotecario", 500000.0, List.of(12,24,36,48,60));
			loanRepository.save(hipo);
			Loan pers = new Loan("Personal", 100000.0, List.of(6,12,24));
			loanRepository.save(pers);
			Loan automot = new Loan("Automotriz", 300000.0, List.of(6,12,24,36));
			loanRepository.save(automot);

			ClientLoan hipo1 = new ClientLoan(60,400000.0, melba,hipo);
			clientLoanRepository.save(hipo1);
			ClientLoan pers1 = new ClientLoan(12,50000,melba,pers);
			clientLoanRepository.save(pers1);

			ClientLoan pers2 = new ClientLoan(24,10000,ana,pers);
			clientLoanRepository.save(pers2);
			ClientLoan automot1 = new ClientLoan(36,20000,ana,automot);
			clientLoanRepository.save(automot1);

			LocalDate hoy = LocalDate.now();

			Card card1 = new Card(melba.getFirstName()+" "+melba.getLastName(), 2548,896,hoy,hoy.plusYears((long) 5),CardColor.GOLD,CardType.DEBIT,melba, true);
			cardRepository.save(card1);
			Card card2 = new Card(melba.getFirstName()+" "+melba.getLastName(),3658,654,hoy,LocalDate.parse("2028-01-28"),CardColor.TITANIUM,CardType.CREDIT,melba, true);
			cardRepository.save(card2);
			Card card3 = new Card(ana.getFirstName()+" "+ana.getLastName(),8956,784,hoy,hoy.plusYears((long) 10),CardColor.TITANIUM,CardType.CREDIT,ana, true);
			cardRepository.save(card3);

			//System.out.println(clientRepository.findByEmail("melba@mindhub.com").getAccounts().size());

			System.out.println(":-D");
			System.out.println("Tranquila Ana, he compilado bien!");
			/*int minNum = 100;
			int maxNum = 999;
			int numRandom (minNum, maxNum) {
				return (int) ((Math.random() * (minNum - maxNum) + minNum));
			}
			System.out.println(numRandom);*/

		};
	}

}
