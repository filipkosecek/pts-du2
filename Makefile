create:
	cat <<EOF | sqlite3 --init src/sql/tables.sql database.db

clean:
	rm database.db
