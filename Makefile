testDB:
	cat <<EOF | sqlite3 --init 
	sqlite3 testDB.db < src/sql ... treba dopisat

clean:
	rm testDB.db
