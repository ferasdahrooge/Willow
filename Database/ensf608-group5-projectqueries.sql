-- 1) Show all tables and explain how they are related to one another (keys, triggers,etc.) 
-- Should be done in the report

-- 2) A basic retrieval query
-- Find web user information with FName: Eric
SELECT * 
FROM web_user 
WHERE FName = 'Eric';

-- 3) A retrieval query with ordered results
-- Find listings priced greater than $500,000 in descending order
SELECT * 
FROM property
WHERE CurrentPrice > 500000
ORDER BY CurrentPrice DESC;

-- 4) A nested retrieval query
-- Find listings priced less than the average current price in the market
SELECT *
FROM property
WHERE CurrentPrice < (
	SELECT AVG(CurrentPrice)
	FROM property);
    
-- 5) A retrieval query using joined tables
-- Find property details and neighbourhood scores for listing with more than 4 bedrooms.
SELECT P.MLS, P.Address, P.CurrentPrice, P.PropertyType, P.LotSize, N.WalkScore, N.BikeScore
FROM property AS P
JOIN neighbourhood AS N ON P.NId = N.Id
WHERE P.Bedrooms > 4;

-- 6) an update operation with a necessary triggers
-- check price_history table for MLS = 'E4320357' prior to update operation 
SELECT * FROM price_history WHERE MLS = 'E4320357';
-- perform update operation on listing, changing the price
UPDATE property 
SET CurrentPrice = 120000 
WHERE MLS = 'E4320357';
-- check the newest addition in price_history for MLS = 'E4320357'
SELECT * FROM PRICE_HISTORY WHERE MLS = 'E4320357';
-- check the change in property for MLS = 'E4320357' after update operation
SELECT DateListed, CurrentPrice FROM PROPERTY WHERE MLS = 'E4320357';

-- 7) a deletion operation with any necessary triggers

-- check that MLS = 'E4320309' exists
SELECT * FROM property WHERE MLS = 'E4320309';
-- check the number of listings for neighbourhood Id = 2 as MLS = 'E4320309' is tied to NId = 2
SELECT * FROM neighbourhood WHERE Id = 2;
-- there are 9 listings with neighbourhood Id = 2
-- perform deletion operation on MLS = 'E4320309'
DELETE FROM property WHERE MLS = 'E4320309';
-- check that MLS = 'E4320309' no long exists
SELECT * FROM property WHERE MLS = 'E4320309';
-- check number of listings for neighbourhood Id = 2 to reflect the deletion. 
SELECT * FROM neighbourhood WHERE Id = 2;
-- there are now only 8 listings with neighbourhood Id = 2!