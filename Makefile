create:
	@[ -f database.db ] && rm database.db || true
	cat <<EOF | sqlite3 --init src/sql/tables.sql database.db
	sqlite3 database.db < src/sql/inserts.sql
clean:
	@[ -f database.db ] && rm database.db || true
