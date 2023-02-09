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
package com.redhat.parodos.workflow.context;

import com.redhat.parodos.workflow.exception.MissingParameterException;
import com.redhat.parodos.workflows.work.WorkContext;

/**
 * 
 * Contains useful logic that is valuable for any WorkFlowTask implementation
 * 
 * @author Luke Shannon (Github: lshannon)
 * @author Richard Wang (Github: richardw98)
 * @author Annel Ketcha (Github: anludke)
 */
public class WorkContextDelegate {
	
	private WorkContextDelegate() {
	}
	
	private static String spaceChar = " ";
    private static String underscoreChar = "_";

    public enum ProcessType {
        PROJECT,
        WORKFLOW_DEFINITION,
        WORKFLOW_TASK_DEFINITION,
        WORKFLOW_EXECUTION,
        WORKFLOW_TASK_EXECUTION
    }

    public enum Resource {
        ID,
        NAME,
        PARAMETERS,
        ARGUMENTS,
        STATUS,
        INFRASTRUCTURE_OPTIONS
    }

    public static String buildKey(ProcessType processType, Resource resource) {
        return String.format("%s%s%s",
                processType.name(),
                underscoreChar,
                resource.name()
        ).toUpperCase();
    }

    public static String buildKey(ProcessType processType, String processName, Resource resource) {
        return String.format("%s%s%s%s%s",
                processType.name(),
                underscoreChar,
                processName.replace(spaceChar, underscoreChar),
                underscoreChar,
                resource.name()
        ).toUpperCase();
    }

    public static Object read(WorkContext workContext, ProcessType processType, String processName, Resource resource) {
        return workContext.get(buildKey(processType, processName, resource));
    }

    public static void write(WorkContext workContext, ProcessType processType, String processName, Resource resource, Object object) {
        workContext.put(buildKey(processType, processName, resource), object);
    }

    public static Object read(WorkContext workContext, ProcessType processType, Resource resource) {
        return workContext.get(buildKey(processType, resource));
    }

    public static void write(WorkContext workContext, ProcessType processType, Resource resource, Object object) {
        workContext.put(buildKey(processType, resource), object);
    }
	
	/**
	 * 
	 * Gets a String value from the WorkContext
	 * 
	 * @param workContext reference from the workflow-engine that is shared across WorkFlowTasks. It wraps a Map
	 * @param key used to put/get values from the WorkContext
	 * @return String value from the Map
	 * 
	 * @throws MissingParameterException if the value not found
	 */
	public static String getRequiredValueFromRequestParams(WorkContext workContext, String key) throws MissingParameterException {
        if (workContext.get(key) == null) {
            throw new MissingParameterException("For this task the WorkContext required key: " + key + " and a corresponding value");
        }
        return (String) workContext.get(key);
    }

	/**
	 * Gets a String value from the WorkContext
	 * 
	 * @param workContext reference from the workflow-engine that is shared across WorkFlowTasks. It wraps a Map
	 * @param key used to put/get values from the WorkContext
	 * @return String value from the Map or a null if that key does not exist
	 * 
	 */
	public static String getOptionalValueFromRequestParams(WorkContext workContext, String key, String defaultValue) {
        if (workContext.get(key) == null) {
            return defaultValue;
        }
        return (String) workContext.get(key);
    }
}
