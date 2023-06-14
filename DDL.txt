CREATE TABLE `Customer` (
	`id`	varchar(100)	NOT NULL,
	`password`	varchar(100)	NOT NULL,
	`name`	varchar(100)	NOT NULL,
	`email`	varchar(100)	NOT NULL,
	`phoneNumber`	bigint	NOT NULL,
	`address`	varchar(100)	NOT NULL,
	`hasHome`	boolean	NOT NULL,
	`hasWorkplace`	boolean	NOT NULL
);

CREATE TABLE `Employee` (
	`id`	varchar(100)	NOT NULL,
	`password`	varchar(100)	NOT NULL,
	`department`	varchar(100)	NOT NULL,
	`name`	varchar(100)	NOT NULL,
	`email`	varchar(100)	NOT NULL,
	`phoneNumber`	bigint	NOT NULL,
	`rank`	varchar(100)	NOT NULL
);

CREATE TABLE `Insurance` (
	`id`	int	NOT NULL,
	`calculationFormulaId`	int	NOT NULL,
	`name`	varchar(100)	NOT NULL,
	`type`	varchar(100)	NOT NULL,
	`target`	varchar(100)	NOT NULL,
	`compensationCondition`	varchar(100)	NOT NULL,
	`notCompensationCondition`	varchar(100)	NOT NULL,
	`status`	varchar(100)	NOT NULL
);

CREATE TABLE `CalculationFormula` (
	`id`	int	NOT NULL,
	`name`	varchar(100)	NOT NULL,
	`riskLevelAccordingToPillarType`	blob	NOT NULL,
	`riskLevelAccordingToRoofType`	blob	NOT NULL,
	`riskLevelAccordingToOutwallType`	blob	NOT NULL,
	`multiplierForMinCompensation`	int	NOT NULL,
	`multiplierForMaxCompensation`	int	NOT NULL,
	`multiplierForPayment`	int	NOT NULL
);

CREATE TABLE `HomeFormula` (
	`id`	int	NOT NULL,
	`riskLevelAccordingToResidenceType`	blob	NOT NULL,
	`riskLevelAccordingToHouseType`	blob	NOT NULL,
	`riskLevelAccordingToSquareMeter`	blob	NOT NULL,
	`riskLevelAccordingToCompensation`	blob	NOT NULL
);

CREATE TABLE `WorkplaceFormula` (
	`id`	int	NOT NULL,
	`riskLevelAccordingToUsage`	blob	NOT NULL,
	`riskLevelAccordingToFloor`	blob	NOT NULL,
	`riskLevelAccordingToSquareFeet`	blob	NOT NULL,
	`riskLevelAccordingToCompensation`	blob	NOT NULL
);

CREATE TABLE `Contract` (
	`id`	int	NOT NULL,
	`customerInfoId`	int	NOT NULL,
	`customerId`	varchar(100)	NOT NULL,
	`insuranceId`	int	NOT NULL,
	`saleEmployeeId`	varchar(100)	NULL,
	`term`	int	NOT NULL,
	`startDate`	timestamp	NULL,
	`expirationDate`	timestamp	NULL,
	`paymentFee`	int	NOT NULL,
	`paymentCycle`	int	NOT NULL,
	`paymentDeadline`	timestamp	NULL,
	`compensation`	int	NOT NULL,
	`contractStatus`	varchar(100)	NOT NULL
);

CREATE TABLE `CustomerInfo` (
	`id`	int	NOT NULL,
	`customerId`	varchar(100)	NOT NULL,
	`squareMeter`	int	NOT NULL,
	`pillarType`	varchar(100)	NOT NULL,
	`roofType`	varchar(100)	NOT NULL,
	`outwallType`	varchar(100)	NOT NULL
);

CREATE TABLE `HomeCustomerInfo` (
	`id`	int	NOT NULL,
	`houseType`	varchar(100)	NOT NULL,
	`residenceType`	varchar(100)	NOT NULL
);

CREATE TABLE `WorkplaceCustomerInfo` (
	`id`	int	NOT NULL,
	`usage`	varchar(100)	NOT NULL,
	`floor`	int	NOT NULL
);

CREATE TABLE `Accident` (
	`id`	int	NOT NULL,
	`contractId`	int	NOT NULL,
	`date`	timestamp	NOT NULL,
	`location`	varchar(100)	NOT NULL,
	`cause`	varchar(100)	NOT NULL,
	`content`	varchar(100)	NOT NULL,
	`damage`	bigint	NOT NULL,
	`accountNumber`	varchar(100)	NOT NULL,
	`stauts`	varchar(100)	NOT NULL
);

CREATE TABLE `Compensation` (
	`id`	int	NOT NULL,
	`accidentId`	int	NOT NULL,
	`compensation`	int	NOT NULL
);

CREATE TABLE `Pay` (
	`id`	int	NOT NULL,
	`contractId`	int	NOT NULL,
	`cardNumber`	varchar(100)	NOT NULL
);

CREATE TABLE `Sale` (
	`id`	int	NOT NULL,
	`saleEmployeeId`	varchar(100)	NOT NULL,
	`customerId`	varchar(100)	NOT NULL,
	`insuranceId`	int	NOT NULL,
	`message`	varchar(100)	NOT NULL
);

ALTER TABLE `Customer` ADD CONSTRAINT `PK_CUSTOMER` PRIMARY KEY (
	`id`
);

ALTER TABLE `Employee` ADD CONSTRAINT `PK_EMPLOYEE` PRIMARY KEY (
	`id`
);

ALTER TABLE `Insurance` ADD CONSTRAINT `PK_INSURANCE` PRIMARY KEY (
	`id`
);

ALTER TABLE `CalculationFormula` ADD CONSTRAINT `PK_CALCULATIONFORMULA` PRIMARY KEY (
	`id`
);

ALTER TABLE `HomeFormula` ADD CONSTRAINT `PK_HOMEFORMULA` PRIMARY KEY (
	`id`
);

ALTER TABLE `WorkplaceFormula` ADD CONSTRAINT `PK_WORKPLACEFORMULA` PRIMARY KEY (
	`id`
);

ALTER TABLE `Contract` ADD CONSTRAINT `PK_CONTRACT` PRIMARY KEY (
	`id`
);

ALTER TABLE `CustomerInfo` ADD CONSTRAINT `PK_CUSTOMERINFO` PRIMARY KEY (
	`id`
);

ALTER TABLE `HomeCustomerInfo` ADD CONSTRAINT `PK_HOMECUSTOMERINFO` PRIMARY KEY (
	`id`
);

ALTER TABLE `WorkplaceCustomerInfo` ADD CONSTRAINT `PK_WORKPLACECUSTOMERINFO` PRIMARY KEY (
	`id`
);

ALTER TABLE `Accident` ADD CONSTRAINT `PK_ACCIDENT` PRIMARY KEY (
	`id`
);

ALTER TABLE `Compensation` ADD CONSTRAINT `PK_COMPENSATION` PRIMARY KEY (
	`id`
);

ALTER TABLE `Pay` ADD CONSTRAINT `PK_PAY` PRIMARY KEY (
	`id`
);

ALTER TABLE `Sale` ADD CONSTRAINT `PK_SALE` PRIMARY KEY (
	`id`
);

ALTER TABLE `Insurance` ADD CONSTRAINT `FK_CalculationFormula_TO_Insurance_1` FOREIGN KEY (
	`calculationFormulaId`
)
REFERENCES `CalculationFormula` (
	`id`
)ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `HomeFormula` ADD CONSTRAINT `FK_CalculationFormula_TO_HomeFormula_1` FOREIGN KEY (
	`id`
)
REFERENCES `CalculationFormula` (
	`id`
)ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `WorkplaceFormula` ADD CONSTRAINT `FK_CalculationFormula_TO_WorkplaceFormula_1` FOREIGN KEY (
	`id`
)
REFERENCES `CalculationFormula` (
	`id`
)ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `Contract` ADD CONSTRAINT `FK_CustomerInfo_TO_Contract_1` FOREIGN KEY (
	`customerInfoId`
)
REFERENCES `CustomerInfo` (
	`id`
)ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `Contract` ADD CONSTRAINT `FK_Customer_TO_Contract_1` FOREIGN KEY (
	`customerId`
)
REFERENCES `Customer` (
	`id`
)ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `Contract` ADD CONSTRAINT `FK_Insurance_TO_Contract_1` FOREIGN KEY (
	`insuranceId`
)
REFERENCES `Insurance` (
	`id`
)ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `CustomerInfo` ADD CONSTRAINT `FK_Customer_TO_CustomerInfo_1` FOREIGN KEY (
	`customerId`
)
REFERENCES `Customer` (
	`id`
)ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `HomeCustomerInfo` ADD CONSTRAINT `FK_CustomerInfo_TO_HomeCustomerInfo_1` FOREIGN KEY (
	`id`
)
REFERENCES `CustomerInfo` (
	`id`
)ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `WorkplaceCustomerInfo` ADD CONSTRAINT `FK_CustomerInfo_TO_WorkplaceCustomerInfo_1` FOREIGN KEY (
	`id`
)
REFERENCES `CustomerInfo` (
	`id`
)ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `Accident` ADD CONSTRAINT `FK_Contract_TO_Accident_1` FOREIGN KEY (
	`contractId`
)
REFERENCES `Contract` (
	`id`
)ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `Compensation` ADD CONSTRAINT `FK_Accident_TO_Compensation_1` FOREIGN KEY (
	`accidentId`
)
REFERENCES `Accident` (
	`id`
)ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `Pay` ADD CONSTRAINT `FK_Contract_TO_Pay_1` FOREIGN KEY (
	`contractId`
)
REFERENCES `Contract` (
	`id`
)ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `Sale` ADD CONSTRAINT `FK_Employee_TO_Sale_1` FOREIGN KEY (
	`saleEmployeeId`
)
REFERENCES `Employee` (
	`id`
)ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `Sale` ADD CONSTRAINT `FK_Customer_TO_Sale_1` FOREIGN KEY (
	`customerId`
)
REFERENCES `Customer` (
	`id`
)ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `Sale` ADD CONSTRAINT `FK_Insurance_TO_Sale_1` FOREIGN KEY (
	`insuranceId`
)
REFERENCES `Insurance` (
	`id`
)ON DELETE CASCADE ON UPDATE CASCADE;
