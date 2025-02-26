/*
 * Copyright (c) 2022 Red Hat Developer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.redhat.parodos.workflow.execution.entity.converter;

import com.redhat.parodos.workflow.enums.WorkFlowStatus;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Converts WorkFlow status into values that can be persisted into a DB column
 *
 * @author Richard Wang (Github: RichardW98)
 * @author Annel Ketcha (Github: anludke)
 */

@Converter(autoApply = true)
public class WorkFlowStatusConverter implements AttributeConverter<WorkFlowStatus, String> {

	@Override
	public String convertToDatabaseColumn(WorkFlowStatus status) {
		return status.name();
	}

	@Override
	public WorkFlowStatus convertToEntityAttribute(String dbData) {
		return WorkFlowStatus.valueOf(dbData);
	}

}
