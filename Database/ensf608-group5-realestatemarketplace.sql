DROP DATABASE IF EXISTS realestatemarketplace;
CREATE DATABASE realestatemarketplace; 
USE realestatemarketplace;

-- -------------
-- CREATE TABLES
-- -------------
DROP TABLE IF EXISTS web_user;
CREATE TABLE web_user (
	Email			varchar(255)	not null,
    FName			varchar(30)		not null,
	LName			varchar(30)		not null,
	Phone			varchar(10)		not null,
    LoginPassword	varchar(30)		not null,
    primary key (Email)
);

DROP TABLE IF EXISTS re_agent;
CREATE TABLE re_agent (
	Email 				varchar(255)	not null,
    Company				varchar(30),
    Rating				integer,
    SecondLanguage		varchar(30),
    primary key (Email),
    foreign key (Email) references web_user (Email)
    ON DELETE CASCADE
);

DROP TABLE IF EXISTS neighbourhood;
CREATE TABLE neighbourhood (
	Id					integer	not null,
    WalkScore			float,
    BikeScore			float,
    NumberOfListings	integer not null default 0, 
    primary key (Id)
);

DROP TABLE IF EXISTS property;
CREATE TABLE property (
	MLS					varchar(10)		not null,
    Address				varchar(255)	not null,
    CurrentPrice		float			not null,
    DateListed			date			not null,
    PropertyType		varchar(30),
    YearBuilt			integer,
    Bedrooms			integer			not null,
    Bathrooms			integer			not null,
    LotSize				float			not null,
    SoldBy				varchar(255)	not null,
    NId					integer			not null,
    primary key (MLS),
    foreign key (NId) references neighbourhood(Id),
    foreign key (SoldBy) references re_agent(email)
    ON DELETE CASCADE
);

DROP TABLE IF EXISTS amenities;
CREATE TABLE amenities (
	NId			integer		not null,
    Amenity		varchar(30)	not null,
    primary key (NId, Amenity),
    foreign key (NId) references neighbourhood(Id)
    ON DELETE CASCADE
);
    
DROP TABLE IF EXISTS price_history;
CREATE TABLE price_history (
	MLS				varchar(10)	not null,
	DateChanged		date	not null default (current_date),
    Price			float	not null,
    primary key (MLS, DateChanged),
    foreign key (MLS) references property(MLS)
    ON DELETE CASCADE
);

DROP TABLE IF EXISTS favourites;
CREATE TABLE favourites (
	Email			varchar(255)	not null,
    MLS				varchar(10)		not null,
    DateSaved		date,
    primary key (Email, MLS),
    foreign key(Email) references web_user(Email),
    foreign key(MLS) references property(MLS)
    ON DELETE CASCADE
);

DROP TABLE IF EXISTS appointment;
CREATE TABLE appointment (
	BuyerEmail			varchar(255)	not null,
    SellerEmail			varchar(255)	not null, 
    MLS					varchar(10)		not null,
    AppointmentTime		datetime		not null,
    primary key (BuyerEmail, SellerEmail, MLS, AppointmentTime),
    foreign key (BuyerEmail) references web_user(Email),
    foreign key (SellerEmail) references re_agent(Email),
    foreign key (MLS) references property(MLS)
    ON DELETE CASCADE
);


-- -------------------------------------------------------------
-- CREATE TRIGGERS: AFTER UPDATE, AFTER INSERT, AND AFTER DELETE
-- -------------------------------------------------------------
DROP TRIGGER IF EXISTS pricechange;
DELIMITER $$
CREATE TRIGGER pricechange
AFTER UPDATE 
ON property FOR EACH ROW
BEGIN
	IF (OLD.CurrentPrice <> NEW.CurrentPrice) THEN
		INSERT INTO price_history(MLS, DateChanged, Price)
		VALUES(old.MLS, current_date(), new.CurrentPrice);
    END IF;
END$$
DELIMITER ;

DROP TRIGGER IF EXISTS listcountoninsert;
CREATE TRIGGER listcountoninsert 
AFTER INSERT 
ON property FOR EACH ROW
	UPDATE neighbourhood SET NumberOfListings = NumberOfListings + 1
    WHERE Id = NEW.NId; 

DROP TRIGGER IF EXISTS listcountondelete;
CREATE TRIGGER listcountondelete
AFTER DELETE
ON property FOR EACH ROW
	UPDATE neighbourhood SET NumberOfListings = NumberOfListings - 1
    WHERE Id = OLD.NId;
    
DROP TRIGGER IF EXISTS appointmentcheck;
DELIMITER $$
CREATE TRIGGER appointmentcheck
BEFORE INSERT ON appointment
FOR EACH ROW
BEGIN
	IF (NEW.SellerEmail <> (SELECT SoldBy FROM property WHERE property.MLS = NEW.MLS)) THEN
		Signal SQLstate '45000'
        SET message_text = "This agent has not listed this property.";
    END IF;
END$$
DELIMITER ;


-- -----------------------    
-- INSERT DATA INTO TABLES
-- -----------------------
INSERT INTO web_user (Email, FName, LName, Phone, LoginPassword)
VALUES
('eric@ucalgary.ca', 'Eric', 'Chung', '4031012222', 'ericp'),
('will@ucalgary.ca', 'Will', 'Ngyuen', '4032023333', 'willp'),
('ardit@ucalgary.ca', 'Ardit', 'Baboci', '4033034444', 'arditp'),
('feras@ucalgary.ca', 'Feras', 'Dahrooge', '4034045555', 'ferasp');

INSERT INTO re_agent (Email, Company, Rating, SecondLanguage)
VALUES
('ardit@ucalgary.ca', 'ABC', '9', 'Albanian'),
('feras@ucalgary.ca', 'XYZ', '7', 'Arabic');

INSERT INTO neighbourhood (Id, WalkScore, BikeScore)
VALUES
(1, 7.6, 6.6),
(2, 8.7, 7.7),
(3, 9.8, 8.8);

INSERT INTO property (MLS, Address, CurrentPrice, DateListed, PropertyType, YearBuilt, Bedrooms, Bathrooms, LotSize, SoldBy, NId)
VALUES
('E4320360', '14611 95th St NW, Edmonton, AB T5E 3Y7', 575000, '2022-02-07', 'House', 1987, 6, 2, 1513, 'ardit@ucalgary.ca', 3),
('E4320364', '10465 42nd Ave NW, Edmonton, AB T6J 7C7', 429900, '2022-01-10', 'Condo', 2012, 2, 3, 1215, 'feras@ucalgary.ca', 2),
('E4320358', '10346 142nd St NW, Edmonton, AB T5N 2P1', 393000, '2022-04-05', 'House', 2007, 5, 2, 1387, 'ardit@ucalgary.ca', 3),
('E4320355', '5512 145th Ave NW, Edmonton, AB T5A 3R3', 185000, '2022-11-21', 'House', 2012, 3, 3, 1019, 'ardit@ucalgary.ca', 3),
('E4320309', '4611 128th Ave NW, Edmonton, AB T5A 2M7', 399900, '2022-03-10', 'House', 1980, 4, 3, 1475, 'feras@ucalgary.ca', 2),
('E4320357', '7339 S Terwillegar Dr NW #1418, Edmonton, AB T6R 0M1', 130000, '2022-09-13', 'Condo', 1951, 1, 1, 631, 'feras@ucalgary.ca', 3),
('E4320331', '1711 109th St NW, Edmonton, AB T6J 5Z8', 519900, '2022-05-20', 'House', 1984, 5, 4, 2161, 'feras@ucalgary.ca', 3),
('E4320291', '13239 Delwood Rd NW, Edmonton, AB T5C 3B5', 299990, '2022-07-13', 'House', 1958, 5, 2, 1072, 'feras@ucalgary.ca', 1),
('E4320295', '12631 161st Ave NW, Edmonton, AB T5X 4W7', 350000, '2022-04-19', 'House', 2007, 4, 3, 1083, 'ardit@ucalgary.ca', 2),
('C4320250', '64 S Citadel Dr NW, Calgary, AB T3G 3V1', 529900, '2022-10-22', 'House', 2010, 3, 3, 1620, 'feras@ucalgary.ca', 1),
('C4320251', '957 S Rundlecairn Way NE, Calgary, AB T1Y 2W7', 429900, '2022-07-25', 'House', 1988, 5, 3, 1223, 'feras@ucalgary.ca', 3),
('C4320252', '220 W Deer Park Pl SE, Calgary, AB T2J 5M2', 499900, '2022-04-13', 'House', 2003, 4, 3, 1210, 'ardit@ucalgary.ca', 2),
('C4320253', '10 Sage Hill Way NW #111, Calgary, AB T3R 0H5', 369900, '2022-01-13', 'Condo', 2010, 2, 2, 850, 'feras@ucalgary.ca', 1),
('C4320254', '425 N 12th Ave NE, Calgary, AB T2E 1A7', 799500, '2022-01-15', 'House', 1977, 4, 3, 1858, 'feras@ucalgary.ca', 2),
('C4320255', '3519 N 49th St NW #28, Calgary, AB T3A 2C7', 189000, '2022-04-10', 'Condo', 1983, 2, 1, 815, 'ardit@ucalgary.ca', 2),
('C4320256', '2232 Langriville Dr SW, Calgary, AB T3E 5G7', 925000, '2022-01-20', 'House', 1986, 5, 3, 1448, 'feras@ucalgary.ca', 2),
('C4320257', '256 E Edgebank Cir NW, Calgary, AB T3A 4W1', 968000, '2022-03-11', 'House', 1952, 5, 3, 2709, 'ardit@ucalgary.ca', 2),
('C4320258', '3415 NE Bonita Cres NW, Calgary, AB T3B 2R9', 499000, '2022-06-08', 'House', 1983, 8, 2, 1905, 'feras@ucalgary.ca', 2);

INSERT INTO amenities (NId, Amenity)
VALUES
(1, 'Playground'),
(1, 'School'),
(2, 'Tennis Court'),
(2, 'Gym'),
(3, 'Golf Course'),
(3, 'Mall');

INSERT INTO price_history (MLS, DateChanged, Price)
VALUES
('E4320360', '2022-02-07', 575000),
('E4320364', '2022-01-10', 429900),
('E4320358', '2022-04-05', 393000),
('E4320355', '2022-11-21', 185000),
('E4320309', '2022-03-10', 399900),
('E4320357', '2022-09-13', 130000),
('E4320331', '2022-05-20', 519900),
('E4320291', '2022-07-13', 299990),
('E4320295', '2022-04-19', 350000),
('C4320250', '2022-10-22', 529900),
('C4320251', '2022-07-25', 429900),
('C4320252', '2022-04-13', 499900),
('C4320253', '2022-01-13', 369900),
('C4320254', '2022-01-15', 799500),
('C4320255', '2022-04-10', 189000),
('C4320256', '2022-01-20', 925000),
('C4320257', '2022-03-11', 968000),
('C4320258', '2022-06-08', 499000),
('E4320360', '2022-03-25', 546693),
('E4320364', '2022-02-07', 402308),
('E4320358', '2022-05-22', 372353),
('E4320355', '2022-11-22', 144761),
('E4320309', '2022-04-23', 379486),
('E4320309', '2022-11-23', 300000);

INSERT INTO favourites (Email, MLS, DateSaved)
VALUES
('eric@ucalgary.ca', 'E4320360', '2022-10-01'),
('will@ucalgary.ca', 'E4320364', '2022-10-02'),
('will@ucalgary.ca', 'E4320358', '2022-10-03');

INSERT INTO appointment (BuyerEmail, SellerEmail, MLS, AppointmentTime)
VALUES
('eric@ucalgary.ca', 'ardit@ucalgary.ca', 'E4320360', '2022-12-01'),
('will@ucalgary.ca', 'feras@ucalgary.ca', 'E4320364', '2022-12-15');