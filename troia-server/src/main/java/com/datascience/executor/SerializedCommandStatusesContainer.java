package com.datascience.executor;

import com.datascience.service.IRandomUniqIDGenerator;
import com.datascience.core.storages.serialization.ISerializer;
import com.datascience.core.storages.serialization.Serialized;

public class SerializedCommandStatusesContainer extends
		CommandStatusesContainer {

	protected ISerializer serializer;

	public SerializedCommandStatusesContainer(IRandomUniqIDGenerator idGenerator, ISerializer serializer) {
		super(idGenerator);
		this.serializer = serializer;
	}
	
	@Override
	public void addCommandStatus(String id, CommandStatus result){
		if (result.getData() != null) {
			Object serializedData = serializer.getRaw(result.getData());
			result = new CommandStatus(result.getStatus(), new Serialized(serializedData),
					result.getError(), result.getExecutionTime());
		}
		super.addCommandStatus(id, result);
	}
}
