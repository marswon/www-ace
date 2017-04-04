package com.huacainfo.ace.workflow.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.bpmn.model.Artifact;
import org.activiti.bpmn.model.Association;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.ErrorEventDefinition;
import org.activiti.bpmn.model.Event;
import org.activiti.bpmn.model.EventDefinition;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.bpmn.model.Lane;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.bpmn.model.Pool;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.ServiceTask;
import org.activiti.bpmn.model.SignalEventDefinition;
import org.activiti.bpmn.model.SubProcess;
import org.activiti.bpmn.model.TextAnnotation;
import org.activiti.bpmn.model.TimerEventDefinition;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.huacainfo.ace.common.model.UserProp;
import com.huacainfo.ace.common.result.ListResult;
import com.huacainfo.ace.common.result.MessageResponse;
import com.huacainfo.ace.common.result.PageResult;
import com.huacainfo.ace.common.result.SingleResult;
import com.huacainfo.ace.common.tools.CommonBeanUtils;
import com.huacainfo.ace.portal.service.DataBaseLogService;
import com.huacainfo.ace.workflow.dao.WorkflowDao;
import com.huacainfo.ace.workflow.mapper.EventInfoMapper;
import com.huacainfo.ace.workflow.mapper.InfoMapper;
import com.huacainfo.ace.workflow.mapper.ReceiveTaskInfoMapper;
import com.huacainfo.ace.workflow.mapper.SequenceFlowInfoMapper;
import com.huacainfo.ace.workflow.mapper.ServiceTaskInfoMapper;
import com.huacainfo.ace.workflow.mapper.UserTaskInfoMapper;
import com.huacainfo.ace.workflow.service.ActFormService;
import com.huacainfo.ace.workflow.service.WorkflowService;
import com.huacainfo.ace.workflow.vo.ProcessDefinitionVo;
import com.huacainfo.ace.workflow.vo.ProcessInstanceVo;
import com.huacainfo.ace.workflow.vo.TaskVo;

@Service("workflowService")
public class WorkflowServiceImpl implements WorkflowService {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private ManagementService managementService;
	@Autowired
	private FormService formService;

	@Autowired
	private HistoryService historyService;
	@Autowired
	private DataBaseLogService dataBaseLogService;
	@Autowired
	private ActFormService actFormService;
	@Autowired
	private WorkflowDao workflowDao;

	protected ObjectMapper objectMapper = new ObjectMapper();
	protected List<String> eventElementTypes = new ArrayList<String>();
	protected Map<String, InfoMapper> propertyMappers = new HashMap<String, InfoMapper>();

	public WorkflowServiceImpl() {
		eventElementTypes.add("StartEvent");
		eventElementTypes.add("EndEvent");
		eventElementTypes.add("BoundaryEvent");
		eventElementTypes.add("IntermediateCatchEvent");
		eventElementTypes.add("ThrowEvent");

		propertyMappers.put("BoundaryEvent", new EventInfoMapper());
		propertyMappers.put("EndEvent", new EventInfoMapper());
		propertyMappers.put("IntermediateCatchEvent", new EventInfoMapper());
		propertyMappers.put("ReceiveTask", new ReceiveTaskInfoMapper());
		propertyMappers.put("StartEvent", new EventInfoMapper());
		propertyMappers.put("SequenceFlow", new SequenceFlowInfoMapper());
		propertyMappers.put("ServiceTask", new ServiceTaskInfoMapper());
		propertyMappers.put("ThrowEvent", new EventInfoMapper());
		propertyMappers.put("UserTask", new UserTaskInfoMapper());
	}

	@Override
	public MessageResponse deploy(String[] file, String id, UserProp userProp)
			throws Exception {

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageResult<ProcessDefinitionVo> findWorkflowList(String name,
			String category, int start, int limit) throws Exception {
		PageResult<ProcessDefinitionVo> rst = new PageResult<ProcessDefinitionVo>();

		ProcessDefinitionQuery processDefinitionQuery = this.repositoryService
				.createProcessDefinitionQuery();
		if (StringUtils.isNotEmpty(name)) {
			processDefinitionQuery.processDefinitionNameLike(name);
		}
		if (StringUtils.isNotEmpty(category)) {
			processDefinitionQuery.processDefinitionCategoryLike(category);
		}
		List<ProcessDefinition> list = processDefinitionQuery.listPage(start,
				limit);
		for (ProcessDefinition pd : list) {
			ProcessDefinitionVo pv = new ProcessDefinitionVo();
			CommonBeanUtils.copyProperties(pv, pd);
			pv.setHasStartFormKey(pd.hasStartFormKey());
			rst.getRows().add(pv);
		}
		int total = (int) this.repositoryService.createProcessDefinitionQuery()
				.count();
		rst.setTotal(total);
		return rst;
	}

	@Override
	public MessageResponse deleteWorkflowById(String deploymentId,
			UserProp userProp) throws Exception {
		this.repositoryService.deleteDeployment(deploymentId);
		this.dataBaseLogService.log("删除工作流", "工作流", "", deploymentId,
				deploymentId, userProp);
		return new MessageResponse(0, "删除工作流完成！");
	}

	@Override
	public SingleResult<ProcessInstanceVo> startProcessInstanceById(
			String processDefinitionId, Map<String, Object> params,
			UserProp userProp) throws Exception {
		Map<String, Object> var = new HashMap<String, Object>();
		SingleResult<ProcessInstanceVo> rst = new SingleResult<ProcessInstanceVo>();
		var.put("userProp", userProp);
		ProcessInstance instance = this.runtimeService
				.startProcessInstanceById(processDefinitionId, var);
		String appId = instance.getProcessDefinitionId();
		String instId = instance.getProcessInstanceId();
		String formId = formService.getStartFormKey(processDefinitionId);
		String taskId = null;

		ProcessInstanceVo o = new ProcessInstanceVo();
		CommonBeanUtils.copyProperties(o, instance);
		actFormService.saveOrUpdateFormData(params, appId, instId, formId,
				taskId, userProp);
		rst.setValue(o);
		workflowDao.updateStartUser(userProp.getUserId(), instId);
		this.logger.info("appId={}, instId={}, formId={}", appId, instId,
				formId);
		this.dataBaseLogService.log("开启流程实例", instance.getName(), "",
				processDefinitionId, processDefinitionId, userProp);
		rst.setStatus(0);
		rst.setErrorMessage("成功!");
		return rst;
	}

	public SingleResult<String> getStartFormKey(String processDefinitionId,
			UserProp userProp) throws Exception {
		SingleResult<String> rst = new SingleResult<String>();
		String formId = formService.getStartFormKey(processDefinitionId);
		rst.setValue(formId);
		return rst;
	}

	@Override
	public MessageResponse completeTask(String taskId,
			Map<String, Object> variables, UserProp userProp) throws Exception {
		Task task = this.taskService.createTaskQuery().taskId(taskId)
				.singleResult();
		String instId = task.getProcessInstanceId();
		String appId = task.getProcessDefinitionId();
		String formId = task.getFormKey();
		this.taskService.setVariablesLocal(taskId, variables);
		this.taskService.complete(taskId, variables);
		this.actFormService.saveOrUpdateFormData(variables, appId, instId,
				formId, taskId, userProp);
		this.dataBaseLogService
				.log("工作流", "任务提交", "", taskId, taskId, userProp);
		return new MessageResponse(0, "任务提交！");
	}

	private ProcessDefinition getProcessDefinitionByProcessDefinitionId(
			String processDefinitionId) {
		this.logger.info("processDefinitionId->" + processDefinitionId);
		return this.repositoryService.createProcessDefinitionQuery()
				.processDefinitionId(processDefinitionId).singleResult();
	}

	@Override
	public ListResult<TaskVo> findPersonalTasks(UserProp userProp)
			throws Exception {
		this.logger.info("userProp-->{}", userProp.toString());
		ListResult<TaskVo> rst = new ListResult<TaskVo>();
		List<Task> items = this.taskService.createTaskQuery()
				.taskAssignee(userProp.getAccount()).list();
		for (Task o : items) {
			TaskVo e = new TaskVo();
			ProcessDefinition pd = this
					.getProcessDefinitionByProcessDefinitionId(o
							.getProcessDefinitionId());
			e.setAssignee(o.getAssignee());
			e.setCreateTime(o.getCreateTime());
			e.setDescription(o.getDescription());
			e.setExecutionId(o.getExecutionId());
			e.setProcessInstanceId(o.getProcessInstanceId());
			e.setId(o.getId());
			e.setName(pd.getName() + "·" + o.getName() + "·"
					+ userProp.getName());
			e.setPriority(o.getPriority());
			e.setFormKey(o.getFormKey());
			rst.getValue().add(e);
		}
		for (String groupId : userProp.getRole()) {
			items = this.taskService.createTaskQuery()
					.taskCandidateGroup(groupId).list();
			for (Task o : items) {
				TaskVo e = new TaskVo();
				ProcessDefinition pd = this
						.getProcessDefinitionByProcessDefinitionId(o
								.getProcessDefinitionId());
				e.setAssignee(o.getAssignee());
				e.setCreateTime(o.getCreateTime());
				e.setDescription(o.getDescription());
				e.setExecutionId(o.getExecutionId());
				e.setProcessInstanceId(o.getProcessInstanceId());
				e.setId(o.getId());
				e.setFormKey(o.getFormKey());
				e.setName(pd.getName() + "·" + o.getName() + "·"
						+ userProp.getName());
				e.setPriority(o.getPriority());
				rst.getValue().add(e);
			}
		}
		rst.setErrorMessage("加载成功");
		return rst;
	}

	@Override
	public ListResult<TaskVo> findTaskByProcessInstanceId(String instanceId)
			throws Exception {
		ListResult<TaskVo> rst = new ListResult<TaskVo>();
		List<Task> items = this.taskService.createTaskQuery()
				.processInstanceId(instanceId).list();
		for (Task o : items) {
			TaskVo e = new TaskVo();
			ProcessDefinition pd = this
					.getProcessDefinitionByProcessDefinitionId(o
							.getProcessDefinitionId());
			e.setAssignee(o.getAssignee());
			e.setCreateTime(o.getCreateTime());
			e.setDescription(o.getDescription());
			e.setExecutionId(o.getExecutionId());
			e.setId(o.getId());
			e.setName(pd.getName() + "·" + o.getName());
			e.setPriority(o.getPriority());
			rst.getValue().add(e);
		}
		return rst;
	}

	@Override
	public ListResult<TaskVo> findHistoryTaskByProcessInstanceId(
			String instanceId) throws Exception {
		ListResult<TaskVo> rst = new ListResult<TaskVo>();
		List<HistoricTaskInstance> items = this.processEngine
				.getHistoryService().createHistoricTaskInstanceQuery()
				.executionId(instanceId).list();
		for (HistoricTaskInstance o : items) {
			TaskVo e = new TaskVo();
			ProcessDefinition pd = this
					.getProcessDefinitionByProcessDefinitionId(o
							.getProcessDefinitionId());
			e.setAssignee(o.getAssignee());
			e.setCreateTime(o.getCreateTime());
			e.setDescription(o.getDescription());
			e.setExecutionId(o.getExecutionId());
			e.setId(o.getId());
			e.setName(pd.getName() + "·" + o.getName());
			e.setPriority(o.getPriority());
			rst.getValue().add(e);
		}

		return rst;
	}

	public PageResult<Map<String, Object>> findProcessInstanceList(
			Map<String, Object> condition, int start, int limit, String orderBy)
			throws Exception {
		PageResult<Map<String, Object>> rst = new PageResult<Map<String, Object>>();

		List<Map<String, Object>> list = this.workflowDao.findList(condition,
				start, start + limit, orderBy);
		for (Map<String, Object> o : list) {
			List<Map<String, Object>> e = this.workflowDao
					.selectDataByInstId((String) o.get("id"));
			for (Map<String, Object> m : e) {
				o.put((String) m.get("fieldId"), m.get("value"));
			}
		}
		rst.setRows(list);
		if (start <= 1) {
			int allRows = this.workflowDao.findCount(condition);
			rst.setTotal(allRows);
		}
		return rst;
	}

	public String getModelJSON(String processInstanceId) throws Exception {

		ProcessInstance processInstance = runtimeService
				.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		if (processInstance == null) {
			throw new Exception("No process instance found with id "
					+ processInstanceId);
		}

		BpmnModel pojoModel = repositoryService.getBpmnModel(processInstance
				.getProcessDefinitionId());

		if (pojoModel == null || pojoModel.getLocationMap().isEmpty()) {
			throw new Exception(
					"Process definition could not be found with id "
							+ processInstance.getProcessDefinitionId());
		}

		// Fetch process-instance activities
		List<HistoricActivityInstance> activityInstances = historyService
				.createHistoricActivityInstanceQuery()
				.processInstanceId(processInstanceId).list();

		Set<String> completedActivityInstances = new HashSet<String>();
		Set<String> currentElements = new HashSet<String>();
		if (CollectionUtils.isNotEmpty(activityInstances)) {
			for (HistoricActivityInstance activityInstance : activityInstances) {
				if (activityInstance.getEndTime() != null) {
					completedActivityInstances.add(activityInstance
							.getActivityId());
				} else {
					currentElements.add(activityInstance.getActivityId());
				}
			}
		}

		List<Job> jobs = managementService.createJobQuery()
				.processInstanceId(processInstanceId).list();
		if (CollectionUtils.isNotEmpty(jobs)) {
			List<Execution> executions = runtimeService.createExecutionQuery()
					.processInstanceId(processInstanceId).list();
			Map<String, Execution> executionMap = new HashMap<String, Execution>();
			for (Execution execution : executions) {
				executionMap.put(execution.getId(), execution);
			}

			for (Job job : jobs) {
				if (executionMap.containsKey(job.getExecutionId())) {
					currentElements.add(executionMap.get(job.getExecutionId())
							.getActivityId());
				}
			}
		}

		// Gather completed flows
		List<String> completedFlows = gatherCompletedFlows(
				completedActivityInstances, currentElements, pojoModel);

		Set<String> completedElements = new HashSet<String>(
				completedActivityInstances);
		completedElements.addAll(completedFlows);

		ObjectNode displayNode = processProcessElements(pojoModel,
				completedElements, currentElements);

		if (completedActivityInstances != null) {
			ArrayNode completedActivities = displayNode
					.putArray("completedActivities");
			for (String completed : completedActivityInstances) {
				completedActivities.add(completed);
			}
		}

		if (currentElements != null) {
			ArrayNode currentActivities = displayNode
					.putArray("currentActivities");
			for (String current : currentElements) {
				currentActivities.add(current);
			}
		}

		if (completedFlows != null) {
			ArrayNode completedSequenceFlows = displayNode
					.putArray("completedSequenceFlows");
			for (String current : completedFlows) {
				completedSequenceFlows.add(current);
			}
		}
		return displayNode.toString();
	}

	protected List<String> gatherCompletedFlows(
			Set<String> completedActivityInstances,
			Set<String> currentActivityinstances, BpmnModel pojoModel) {

		List<String> completedFlows = new ArrayList<String>();
		List<String> activities = new ArrayList<String>(
				completedActivityInstances);
		activities.addAll(currentActivityinstances);

		// TODO: not a robust way of checking when parallel paths are active,
		// should be revisited
		// Go over all activities and check if it's possible to match any
		// outgoing paths against the activities
		for (FlowElement activity : pojoModel.getMainProcess()
				.getFlowElements()) {
			if (activity instanceof FlowNode) {
				int index = activities.indexOf(activity.getId());
				if (index >= 0 && index + 1 < activities.size()) {
					List<SequenceFlow> outgoingFlows = ((FlowNode) activity)
							.getOutgoingFlows();
					for (SequenceFlow flow : outgoingFlows) {
						String destinationFlowId = flow.getTargetRef();
						if (destinationFlowId.equals(activities.get(index + 1))) {
							completedFlows.add(flow.getId());
						}
					}
				}
			}
		}
		return completedFlows;
	}

	protected ObjectNode processProcessElements(BpmnModel pojoModel,
			Set<String> completedElements, Set<String> currentElements) {
		ObjectNode displayNode = objectMapper.createObjectNode();
		GraphicInfo diagramInfo = new GraphicInfo();

		ArrayNode elementArray = objectMapper.createArrayNode();
		ArrayNode flowArray = objectMapper.createArrayNode();

		if (CollectionUtils.isNotEmpty(pojoModel.getPools())) {
			ArrayNode poolArray = objectMapper.createArrayNode();
			boolean firstElement = true;
			for (Pool pool : pojoModel.getPools()) {
				ObjectNode poolNode = objectMapper.createObjectNode();
				poolNode.put("id", pool.getId());
				poolNode.put("name", pool.getName());
				GraphicInfo poolInfo = pojoModel.getGraphicInfo(pool.getId());
				fillGraphicInfo(poolNode, poolInfo, true);
				org.activiti.bpmn.model.Process process = pojoModel
						.getProcess(pool.getId());
				if (process != null
						&& CollectionUtils.isNotEmpty(process.getLanes())) {
					ArrayNode laneArray = objectMapper.createArrayNode();
					for (Lane lane : process.getLanes()) {
						ObjectNode laneNode = objectMapper.createObjectNode();
						laneNode.put("id", lane.getId());
						laneNode.put("name", lane.getName());
						fillGraphicInfo(laneNode,
								pojoModel.getGraphicInfo(lane.getId()), true);
						laneArray.add(laneNode);
					}
					poolNode.put("lanes", laneArray);
				}
				poolArray.add(poolNode);

				double rightX = poolInfo.getX() + poolInfo.getWidth();
				double bottomY = poolInfo.getY() + poolInfo.getHeight();
				double middleX = poolInfo.getX() + (poolInfo.getWidth() / 2);
				if (firstElement || middleX < diagramInfo.getX()) {
					diagramInfo.setX(middleX);
				}
				if (firstElement || poolInfo.getY() < diagramInfo.getY()) {
					diagramInfo.setY(poolInfo.getY());
				}
				if (rightX > diagramInfo.getWidth()) {
					diagramInfo.setWidth(rightX);
				}
				if (bottomY > diagramInfo.getHeight()) {
					diagramInfo.setHeight(bottomY);
				}
				firstElement = false;
			}
			displayNode.put("pools", poolArray);

		} else {
			// in initialize with fake x and y to make sure the minimal
			// values are set
			diagramInfo.setX(9999);
			diagramInfo.setY(1000);
		}

		for (org.activiti.bpmn.model.Process process : pojoModel.getProcesses()) {
			processElements(process.getFlowElements(), pojoModel, elementArray,
					flowArray, diagramInfo, completedElements, currentElements);
			processArtifacts(process.getArtifacts(), pojoModel, elementArray,
					flowArray, diagramInfo);
		}

		displayNode.put("elements", elementArray);
		displayNode.put("flows", flowArray);

		displayNode.put("diagramBeginX", diagramInfo.getX());
		displayNode.put("diagramBeginY", diagramInfo.getY());
		displayNode.put("diagramWidth", diagramInfo.getWidth());
		displayNode.put("diagramHeight", diagramInfo.getHeight());
		return displayNode;
	}

	protected void fillGraphicInfo(ObjectNode elementNode,
			GraphicInfo graphicInfo, boolean includeWidthAndHeight) {
		commonFillGraphicInfo(elementNode, graphicInfo.getX(),
				graphicInfo.getY(), graphicInfo.getWidth(),
				graphicInfo.getHeight(), includeWidthAndHeight);
	}

	protected void commonFillGraphicInfo(ObjectNode elementNode, double x,
			double y, double width, double height, boolean includeWidthAndHeight) {

		elementNode.put("x", x);
		elementNode.put("y", y);
		if (includeWidthAndHeight) {
			elementNode.put("width", width);
			elementNode.put("height", height);
		}
	}

	protected void fillDiagramInfo(GraphicInfo graphicInfo,
			GraphicInfo diagramInfo) {
		double rightX = graphicInfo.getX() + graphicInfo.getWidth();
		double bottomY = graphicInfo.getY() + graphicInfo.getHeight();
		double middleX = graphicInfo.getX() + (graphicInfo.getWidth() / 2);
		if (middleX < diagramInfo.getX()) {
			diagramInfo.setX(middleX);
		}
		if (graphicInfo.getY() < diagramInfo.getY()) {
			diagramInfo.setY(graphicInfo.getY());
		}
		if (rightX > diagramInfo.getWidth()) {
			diagramInfo.setWidth(rightX);
		}
		if (bottomY > diagramInfo.getHeight()) {
			diagramInfo.setHeight(bottomY);
		}
	}

	protected void processElements(Collection<FlowElement> elementList,
			BpmnModel model, ArrayNode elementArray, ArrayNode flowArray,
			GraphicInfo diagramInfo, Set<String> completedElements,
			Set<String> currentElements) {

		for (FlowElement element : elementList) {

			ObjectNode elementNode = objectMapper.createObjectNode();
			if (completedElements != null) {
				elementNode.put("completed",
						completedElements.contains(element.getId()));
			}

			if (currentElements != null) {
				elementNode.put("current",
						currentElements.contains(element.getId()));
			}

			if (element instanceof SequenceFlow) {
				SequenceFlow flow = (SequenceFlow) element;
				elementNode.put("id", flow.getId());
				elementNode.put("type", "sequenceFlow");
				elementNode.put("sourceRef", flow.getSourceRef());
				elementNode.put("targetRef", flow.getTargetRef());
				List<GraphicInfo> flowInfo = model
						.getFlowLocationGraphicInfo(flow.getId());
				if (CollectionUtils.isNotEmpty(flowInfo)) {
					ArrayNode waypointArray = objectMapper.createArrayNode();
					for (GraphicInfo graphicInfo : flowInfo) {
						ObjectNode pointNode = objectMapper.createObjectNode();
						fillGraphicInfo(pointNode, graphicInfo, false);
						waypointArray.add(pointNode);
						fillDiagramInfo(graphicInfo, diagramInfo);
					}
					elementNode.put("waypoints", waypointArray);

					String className = element.getClass().getSimpleName();
					if (propertyMappers.containsKey(className)) {
						elementNode.put("properties",
								propertyMappers.get(className).map(element));
					}

					flowArray.add(elementNode);
				}

			} else {

				elementNode.put("id", element.getId());
				elementNode.put("name", element.getName());

				GraphicInfo graphicInfo = model.getGraphicInfo(element.getId());
				if (graphicInfo != null) {
					fillGraphicInfo(elementNode, graphicInfo, true);
					fillDiagramInfo(graphicInfo, diagramInfo);
				}

				String className = element.getClass().getSimpleName();
				elementNode.put("type", className);
				fillEventTypes(className, element, elementNode);

				if (element instanceof ServiceTask) {
					ServiceTask serviceTask = (ServiceTask) element;
					if (ServiceTask.MAIL_TASK.equals(serviceTask.getType())) {
						elementNode.put("taskType", "mail");

					} else if ("camel".equals(serviceTask.getType())) {
						elementNode.put("taskType", "camel");

					} else if ("mule".equals(serviceTask.getType())) {
						elementNode.put("taskType", "mule");
					}
				}

				if (propertyMappers.containsKey(className)) {
					elementNode.put("properties", propertyMappers
							.get(className).map(element));
				}

				elementArray.add(elementNode);

				if (element instanceof SubProcess) {
					SubProcess subProcess = (SubProcess) element;
					processElements(subProcess.getFlowElements(), model,
							elementArray, flowArray, diagramInfo,
							completedElements, currentElements);
					processArtifacts(subProcess.getArtifacts(), model,
							elementArray, flowArray, diagramInfo);
				}
			}
		}
	}

	protected void processArtifacts(Collection<Artifact> artifactList,
			BpmnModel model, ArrayNode elementArray, ArrayNode flowArray,
			GraphicInfo diagramInfo) {

		for (Artifact artifact : artifactList) {

			if (artifact instanceof Association) {
				ObjectNode elementNode = objectMapper.createObjectNode();
				Association flow = (Association) artifact;
				elementNode.put("id", flow.getId());
				elementNode.put("type", "association");
				elementNode.put("sourceRef", flow.getSourceRef());
				elementNode.put("targetRef", flow.getTargetRef());
				fillWaypoints(flow.getId(), model, elementNode, diagramInfo);
				flowArray.add(elementNode);

			} else {

				ObjectNode elementNode = objectMapper.createObjectNode();
				elementNode.put("id", artifact.getId());

				if (artifact instanceof TextAnnotation) {
					TextAnnotation annotation = (TextAnnotation) artifact;
					elementNode.put("text", annotation.getText());
				}

				GraphicInfo graphicInfo = model
						.getGraphicInfo(artifact.getId());
				if (graphicInfo != null) {
					fillGraphicInfo(elementNode, graphicInfo, true);
					fillDiagramInfo(graphicInfo, diagramInfo);
				}

				String className = artifact.getClass().getSimpleName();
				elementNode.put("type", className);

				elementArray.add(elementNode);
			}
		}
	}

	protected void fillWaypoints(String id, BpmnModel model,
			ObjectNode elementNode, GraphicInfo diagramInfo) {
		List<GraphicInfo> flowInfo = model.getFlowLocationGraphicInfo(id);
		ArrayNode waypointArray = objectMapper.createArrayNode();
		for (GraphicInfo graphicInfo : flowInfo) {
			ObjectNode pointNode = objectMapper.createObjectNode();
			fillGraphicInfo(pointNode, graphicInfo, false);
			waypointArray.add(pointNode);
			fillDiagramInfo(graphicInfo, diagramInfo);
		}
		elementNode.put("waypoints", waypointArray);
	}

	protected void fillEventTypes(String className, FlowElement element,
			ObjectNode elementNode) {
		if (eventElementTypes.contains(className)) {
			Event event = (Event) element;
			if (CollectionUtils.isNotEmpty(event.getEventDefinitions())) {
				EventDefinition eventDef = event.getEventDefinitions().get(0);
				ObjectNode eventNode = objectMapper.createObjectNode();
				if (eventDef instanceof TimerEventDefinition) {
					TimerEventDefinition timerDef = (TimerEventDefinition) eventDef;
					eventNode.put("type", "timer");
					if (StringUtils.isNotEmpty(timerDef.getTimeCycle())) {
						eventNode.put("timeCycle", timerDef.getTimeCycle());
					}
					if (StringUtils.isNotEmpty(timerDef.getTimeDate())) {
						eventNode.put("timeDate", timerDef.getTimeDate());
					}
					if (StringUtils.isNotEmpty(timerDef.getTimeDuration())) {
						eventNode.put("timeDuration",
								timerDef.getTimeDuration());
					}

				} else if (eventDef instanceof ErrorEventDefinition) {
					ErrorEventDefinition errorDef = (ErrorEventDefinition) eventDef;
					eventNode.put("type", "error");
					if (StringUtils.isNotEmpty(errorDef.getErrorCode())) {
						eventNode.put("errorCode", errorDef.getErrorCode());
					}

				} else if (eventDef instanceof SignalEventDefinition) {
					SignalEventDefinition signalDef = (SignalEventDefinition) eventDef;
					eventNode.put("type", "signal");
					if (StringUtils.isNotEmpty(signalDef.getSignalRef())) {
						eventNode.put("signalRef", signalDef.getSignalRef());
					}

				} else if (eventDef instanceof MessageEventDefinition) {
					MessageEventDefinition messageDef = (MessageEventDefinition) eventDef;
					eventNode.put("type", "message");
					if (StringUtils.isNotEmpty(messageDef.getMessageRef())) {
						eventNode.put("messageRef", messageDef.getMessageRef());
					}
				}
				elementNode.put("eventDefinition", eventNode);
			}
		}
	}
}
