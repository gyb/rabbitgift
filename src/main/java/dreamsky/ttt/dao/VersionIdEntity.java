package dreamsky.ttt.dao;

public interface VersionIdEntity extends IdEntity {
	//version number for optimistic locking
	int getVersion();
	void setVersion(int version);
}
