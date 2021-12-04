create:
	@[ -f database.db ] && rm database.db || true
	cat <<EOF | sqlite3 --init znamky.sql database.db
	
clean:
	@[ -f database.db ] && rm database.db || true
