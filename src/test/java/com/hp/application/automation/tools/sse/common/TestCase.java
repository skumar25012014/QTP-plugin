package com.hp.application.automation.tools.sse.common;

import com.hp.application.automation.tools.model.CdaDetails;

public interface TestCase {
    
    final String URL = "http://16.55.245.168:8081/qcbin";
    final String SERVERNAME = "doc05";
    final String USER = "sa";
    final String PASS = "pwd";
    final String DOMAIN = "DEFAULT";
    final String PROJECT = "dani_demo";
    final String DURATION = "60";
    final String DESCRIPTION = "This is an entity description";
    final String ERROR = "error";
    final String PASSED = "pass";
    final String BVS = "BVS";
    final String TEST_SET = "TEST_SET";
    final String ENTITY_ID = "12";
    final String ENVIRONMENT_CONFIGURATION_ID = "1001";
    final String DEPLOYMENT_ACTION = "Use Deployed";
    final String DEPROVISIONING_ACTION = "Leave environment deployed";
    final String DEPLOYED_ENVIRONMENT_NAME = "EnvName";
    final CdaDetails CDA_DETAILS = new CdaDetails("Deploy", "testCDA", "Deprovision");
    final String TIMESLOT_ID = "3773";
    final String RUN_ID = "10";
    final String POST_RUN_ACTION = "Collate";
    
    final byte[] PC_RUN_ENTITY_DATA_FORMAT =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Entity Type=\"run\"><Fields><Field Name=\"pc-vusers-involved\"/><Field Name=\"pc-state\"><Value></Value></Field><Field Name=\"test-instance\"><Value>1</Value></Field><Field Name=\"execution-date\"><Value>2013-04-09</Value></Field><Field Name=\"pc-end-time\"/><Field Name=\"pc-throughput-average\"/><Field Name=\"results-statistics\"><Value></Value></Field><Field Name=\"os-config\"><Value></Value></Field><Field Name=\"async-task-id\"><Value></Value></Field><Field Name=\"run-ver-stamp\"><Value>3</Value></Field><Field Name=\"pc-total-transact-passed\"/><Field Name=\"vc-version-number\"/><Field Name=\"pc-report-url\"><Value></Value></Field><Field Name=\"cycle-id\"><Value>1</Value></Field><Field Name=\"pc-actual-post-run-action\"><Value></Value></Field><Field Name=\"cycle\"><Value></Value></Field><Field Name=\"host\"><Value></Value></Field><Field Name=\"status\"><Value>N/A</Value></Field><Field Name=\"pc-total-transact-failed\"/><Field Name=\"iters-params-values\"><Value></Value></Field><Field Name=\"test-id\"><Value>9</Value></Field><Field Name=\"sla-calculated-data\"><Value></Value></Field><Field Name=\"sub-status-progress\"><Value></Value></Field><Field Name=\"pc-run-url\"><Value>RuntimeOperations/RunStart.aspx?pcRunID=363&amp;qcRunID=42</Value></Field><Field Name=\"owner\"><Value>sa</Value></Field><Field Name=\"bpta-change-detected\"/><Field Name=\"pc-testset-name\"/><Field Name=\"bpta-change-awareness\"><Value></Value></Field><Field Name=\"execution-time\"><Value>15:52:53</Value></Field><Field Name=\"vc-locked-by\"><Value></Value></Field><Field Name=\"vuds-mode\"><Value>N</Value></Field><Field Name=\"pc-is-copied\"><Value></Value></Field><Field Name=\"os-sp\"><Value></Value></Field><Field Name=\"pc-transact-sec-average\"/><Field Name=\"pc-vusers-average\"/><Field Name=\"sub-status\"><Value></Value></Field><Field Name=\"pc-load-generators\"><Value></Value></Field><Field Name=\"state\"><Value>Run Failure</Value></Field><Field Name=\"id\"><Value>42</Value></Field><Field Name=\"pc-procedure-id\"/><Field Name=\"test-config-id\"><Value>1009</Value></Field><Field Name=\"name\"><Value>AdhocRun_2013-04-09 15:52:51</Value></Field><Field Name=\"has-linkage\"><Value>N</Value></Field><Field Name=\"path\"><Value>1_42</Value></Field><Field Name=\"vc-status\"><Value></Value></Field><Field Name=\"pinned-baseline\"><Value></Value></Field><Field Name=\"pc-hits-sec-average\"/><Field Name=\"pc-vusers-max\"/><Field Name=\"os-build\"><Value></Value></Field><Field Name=\"testcycl-id\"><Value>5</Value></Field><Field Name=\"pc-start-time\"><Value>2013-04-09 15:52:51</Value></Field><Field Name=\"temp-results-dir-path\"><Value></Value></Field><Field Name=\"pc-procedure-name\"/><Field Name=\"pc-reservation-id\"><Value>4172</Value></Field><Field Name=\"assign-rcyc\"><Value></Value></Field><Field Name=\"last-modified\"><Value>2013-04-09 15:52:53</Value></Field><Field Name=\"attachment\"><Value></Value></Field><Field Name=\"os-name\"><Value></Value></Field><Field Name=\"pc-total-errors\"/><Field Name=\"subtype-id\"><Value>hp.pc.run.performance-test</Value></Field><Field Name=\"draft\"><Value>N</Value></Field><Field Name=\"iters-sum-status\"><Value></Value></Field><Field Name=\"duration\"><Value>0</Value></Field><Field Name=\"bpt-structure\"><Value></Value></Field><Field Name=\"text-sync\"><Value></Value></Field><Field Name=\"pc-controller-name\"><Value></Value></Field><Field Name=\"comments\"><Value></Value></Field></Fields><RelatedEntities/></Entity>".getBytes();
    final byte[] PC_FINISHED_DATA =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Entity Type=\"run\"><Fields><Field Name=\"pc-vusers-involved\"/><Field Name=\"pc-state\"><Value></Value></Field><Field Name=\"test-instance\"><Value>1</Value></Field><Field Name=\"execution-date\"><Value>2013-04-09</Value></Field><Field Name=\"pc-end-time\"/><Field Name=\"pc-throughput-average\"/><Field Name=\"results-statistics\"><Value></Value></Field><Field Name=\"os-config\"><Value></Value></Field><Field Name=\"async-task-id\"><Value></Value></Field><Field Name=\"run-ver-stamp\"><Value>3</Value></Field><Field Name=\"pc-total-transact-passed\"/><Field Name=\"vc-version-number\"/><Field Name=\"pc-report-url\"><Value></Value></Field><Field Name=\"cycle-id\"><Value>1</Value></Field><Field Name=\"pc-actual-post-run-action\"><Value></Value></Field><Field Name=\"cycle\"><Value></Value></Field><Field Name=\"host\"><Value></Value></Field><Field Name=\"status\"><Value>N/A</Value></Field><Field Name=\"pc-total-transact-failed\"/><Field Name=\"iters-params-values\"><Value></Value></Field><Field Name=\"test-id\"><Value>9</Value></Field><Field Name=\"sla-calculated-data\"><Value></Value></Field><Field Name=\"sub-status-progress\"><Value></Value></Field><Field Name=\"pc-run-url\"><Value>RuntimeOperations/RunStart.aspx?pcRunID=363&amp;qcRunID=42</Value></Field><Field Name=\"owner\"><Value>sa</Value></Field><Field Name=\"bpta-change-detected\"/><Field Name=\"pc-testset-name\"/><Field Name=\"bpta-change-awareness\"><Value></Value></Field><Field Name=\"execution-time\"><Value>15:52:53</Value></Field><Field Name=\"vc-locked-by\"><Value></Value></Field><Field Name=\"vuds-mode\"><Value>N</Value></Field><Field Name=\"pc-is-copied\"><Value></Value></Field><Field Name=\"os-sp\"><Value></Value></Field><Field Name=\"pc-transact-sec-average\"/><Field Name=\"pc-vusers-average\"/><Field Name=\"sub-status\"><Value></Value></Field><Field Name=\"pc-load-generators\"><Value></Value></Field><Field Name=\"state\"><Value>Run Failure</Value></Field><Field Name=\"id\"><Value>42</Value></Field><Field Name=\"pc-procedure-id\"/><Field Name=\"test-config-id\"><Value>1009</Value></Field><Field Name=\"name\"><Value>AdhocRun_2013-04-09 15:52:51</Value></Field><Field Name=\"has-linkage\"><Value>N</Value></Field><Field Name=\"path\"><Value>1_42</Value></Field><Field Name=\"vc-status\"><Value></Value></Field><Field Name=\"pinned-baseline\"><Value></Value></Field><Field Name=\"pc-hits-sec-average\"/><Field Name=\"pc-vusers-max\"/><Field Name=\"os-build\"><Value></Value></Field><Field Name=\"testcycl-id\"><Value>5</Value></Field><Field Name=\"pc-start-time\"><Value>2013-04-09 15:52:51</Value></Field><Field Name=\"temp-results-dir-path\"><Value></Value></Field><Field Name=\"pc-procedure-name\"/><Field Name=\"pc-reservation-id\"><Value>4172</Value></Field><Field Name=\"assign-rcyc\"><Value></Value></Field><Field Name=\"last-modified\"><Value>2013-04-09 15:52:53</Value></Field><Field Name=\"attachment\"><Value></Value></Field><Field Name=\"os-name\"><Value></Value></Field><Field Name=\"pc-total-errors\"/><Field Name=\"subtype-id\"><Value>hp.pc.run.performance-test</Value></Field><Field Name=\"draft\"><Value>N</Value></Field><Field Name=\"iters-sum-status\"><Value></Value></Field><Field Name=\"duration\"><Value>0</Value></Field><Field Name=\"bpt-structure\"><Value></Value></Field><Field Name=\"text-sync\"><Value></Value></Field><Field Name=\"pc-controller-name\"><Value></Value></Field><Field Name=\"comments\"><Value></Value></Field></Fields><RelatedEntities/></Entity>".getBytes();
    final byte[] PC_RUNNING_DATA =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Entity Type=\"run\"><Fields><Field Name=\"pc-vusers-involved\"/><Field Name=\"pc-state\"><Value></Value></Field><Field Name=\"test-instance\"><Value>1</Value></Field><Field Name=\"execution-date\"><Value>2013-04-09</Value></Field><Field Name=\"pc-end-time\"/><Field Name=\"pc-throughput-average\"/><Field Name=\"results-statistics\"><Value></Value></Field><Field Name=\"os-config\"><Value></Value></Field><Field Name=\"async-task-id\"><Value></Value></Field><Field Name=\"run-ver-stamp\"><Value>3</Value></Field><Field Name=\"pc-total-transact-passed\"/><Field Name=\"vc-version-number\"/><Field Name=\"pc-report-url\"><Value></Value></Field><Field Name=\"cycle-id\"><Value>1</Value></Field><Field Name=\"pc-actual-post-run-action\"><Value></Value></Field><Field Name=\"cycle\"><Value></Value></Field><Field Name=\"host\"><Value></Value></Field><Field Name=\"status\"/><Field Name=\"pc-total-transact-failed\"/><Field Name=\"iters-params-values\"><Value></Value></Field><Field Name=\"test-id\"><Value>9</Value></Field><Field Name=\"sla-calculated-data\"><Value></Value></Field><Field Name=\"sub-status-progress\"><Value></Value></Field><Field Name=\"pc-run-url\"><Value>RuntimeOperations/RunStart.aspx?pcRunID=363&amp;qcRunID=42</Value></Field><Field Name=\"owner\"><Value>sa</Value></Field><Field Name=\"bpta-change-detected\"/><Field Name=\"pc-testset-name\"/><Field Name=\"bpta-change-awareness\"><Value></Value></Field><Field Name=\"execution-time\"><Value>15:52:53</Value></Field><Field Name=\"vc-locked-by\"><Value></Value></Field><Field Name=\"vuds-mode\"><Value>N</Value></Field><Field Name=\"pc-is-copied\"><Value></Value></Field><Field Name=\"os-sp\"><Value></Value></Field><Field Name=\"pc-transact-sec-average\"/><Field Name=\"pc-vusers-average\"/><Field Name=\"sub-status\"><Value></Value></Field><Field Name=\"pc-load-generators\"><Value></Value></Field><Field Name=\"state\"><Value>Run Failure</Value></Field><Field Name=\"id\"><Value>42</Value></Field><Field Name=\"pc-procedure-id\"/><Field Name=\"test-config-id\"><Value>1009</Value></Field><Field Name=\"name\"><Value>AdhocRun_2013-04-09 15:52:51</Value></Field><Field Name=\"has-linkage\"><Value>N</Value></Field><Field Name=\"path\"><Value>1_42</Value></Field><Field Name=\"vc-status\"><Value></Value></Field><Field Name=\"pinned-baseline\"><Value></Value></Field><Field Name=\"pc-hits-sec-average\"/><Field Name=\"pc-vusers-max\"/><Field Name=\"os-build\"><Value></Value></Field><Field Name=\"testcycl-id\"><Value>5</Value></Field><Field Name=\"pc-start-time\"><Value>2013-04-09 15:52:51</Value></Field><Field Name=\"temp-results-dir-path\"><Value></Value></Field><Field Name=\"pc-procedure-name\"/><Field Name=\"pc-reservation-id\"><Value>4172</Value></Field><Field Name=\"assign-rcyc\"><Value></Value></Field><Field Name=\"last-modified\"><Value>2013-04-09 15:52:53</Value></Field><Field Name=\"attachment\"><Value></Value></Field><Field Name=\"os-name\"><Value></Value></Field><Field Name=\"pc-total-errors\"/><Field Name=\"subtype-id\"><Value>hp.pc.run.performance-test</Value></Field><Field Name=\"draft\"><Value>N</Value></Field><Field Name=\"iters-sum-status\"><Value></Value></Field><Field Name=\"duration\"><Value>0</Value></Field><Field Name=\"bpt-structure\"><Value></Value></Field><Field Name=\"text-sync\"><Value></Value></Field><Field Name=\"pc-controller-name\"><Value></Value></Field><Field Name=\"comments\"><Value></Value></Field></Fields><RelatedEntities/></Entity>".getBytes();
    
    final byte[] RUN_ENTITY_DATA_FORMAT =
            String.format(
                    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Entity Type=\"procedure-run\"><Fields><Field Name=\"end-time\"><Value>2012-12-30 11:44:48</Value></Field><Field Name=\"topology-id\"><Value></Value></Field><Field Name=\"status\"><Value></Value></Field><Field Name=\"topology-name\"><Value></Value></Field><Field Name=\"run-type\"><Value>BVS-Run</Value></Field><Field Name=\"state\"><Value>Finished</Value></Field><Field Name=\"start-time\"><Value>2012-12-30 11:44:47</Value></Field><Field Name=\"reservation-id\"><Value>%s</Value></Field><Field Name=\"is-closing\"><Value></Value></Field><Field Name=\"vts\"><Value>2012-12-30 11:44:48</Value></Field><Field Name=\"planner-time-lock\"><Value>2012-12-30 11:44:47</Value></Field><Field Name=\"user-name\"><Value>sa</Value></Field><Field Name=\"id\"><Value>1001</Value></Field><Field Name=\"test-set-ids\"><Value></Value></Field><Field Name=\"parent-id\"><Value>1001</Value></Field><Field Name=\"ver-stamp\"><Value>4</Value></Field><Field Name=\"completed-successfully\"><Value>Y</Value></Field><Field Name=\"name\"><Value>bvsdani1</Value></Field><Field Name=\"user-id\"><Value>251</Value></Field><Field Name=\"test-set-names\"><Value></Value></Field><Field Name=\"step\"><Value>Deprovisioning</Value></Field></Fields><RelatedEntities/></Entity>",
                    TIMESLOT_ID).getBytes();
    final String TIMESLOT_DATA_FORMAT =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Entity Type=\"Reservation\"><Fields><Field Name=\"fail-reason\"><Value><reasons/></Value></Field><Field Name=\"vusers\"><Value>0</Value></Field><Field Name=\"linked-testset-validity\"><Value/></Field><Field Name=\"type\"><Value>Functional Test Set</Value></Field><Field Name=\"current-labrun-id\"><Value>1233</Value></Field><Field Name=\"description\"><Value/></Field><Field Name=\"failure-was-notified\"><Value>N</Value></Field><Field Name=\"duration-in-minutes\"><Value>0</Value></Field><Field Name=\"is-active\"><Value>Y</Value></Field><Field Name=\"linked-process-id\"><Value>1059</Value></Field><Field Name=\"current-run-state\"><Value>%s</Value></Field><Field Name=\"modified-by\"><Value>_pc_system_</Value></Field><Field Name=\"created-by\"><Value>sa</Value></Field><Field Name=\"client-start-time\"><Value>2013-03-03 16:12:10</Value></Field><Field Name=\"linked-testset-name\"><Value>ts1</Value></Field><Field Name=\"linked-process-validity\"><Value>Valid</Value></Field><Field Name=\"linked-lt-testinstance-id\"/><Field Name=\"linked-lt-autostart\"><Value>N</Value></Field><Field Name=\"priority-time\"><Value>2013-03-03 16:12:11</Value></Field><Field Name=\"client-end-time\"><Value>2013-03-03 16:12:34</Value></Field><Field Name=\"vuds-mode\"><Value/></Field><Field Name=\"open-status\"><Value>0</Value></Field><Field Name=\"linked-testset-id\"><Value>1</Value></Field><Field Name=\"linked-process-name\"><Value>ts1_d314fe5a-1433-499e-bda5-900a0aa22fd7</Value></Field><Field Name=\"linked-lt-name\"><Value/></Field><Field Name=\"linked-lt-vusers\"/><Field Name=\"dev-status\"><Value>0</Value></Field><Field Name=\"needs-recalc\"><Value>N</Value></Field><Field Name=\"id\"><Value>3450</Value></Field><Field Name=\"is-allocated\"><Value>Y</Value></Field><Field Name=\"project-id\"><Value>3</Value></Field><Field Name=\"name\"><Value>ts1</Value></Field><Field Name=\"project-uid\"><Value>b3a75e31-06fe-44c2-9de9-3f427273254b</Value></Field><Field Name=\"autostart-attempts\"/><Field Name=\"end-time\"><Value>2013-03-03 16:12:34</Value></Field><Field Name=\"loadgenerators\"><Value>1</Value></Field><Field Name=\"linked-lt-id\"/><Field Name=\"last-modified\"><Value>6</Value></Field><Field Name=\"total-vusers\"><Value>0</Value></Field><Field Name=\"linked-lt-validity\"><Value/></Field><Field Name=\"start-time\"><Value>2013-03-03 16:12:10</Value></Field><Field Name=\"creation-date\"><Value>2013-03-03 16:12:10</Value></Field><Field Name=\"vts\"><Value>2013-03-03 16:12:34</Value></Field><Field Name=\"alloc-state-change-date\"><Value>2013-03-03 16:12:10</Value></Field><Field Name=\"linked-lti-exists\"><Value>Y</Value></Field><Field Name=\"linked-lt-post-run\"><Value/></Field><Field Name=\"is-ad-hoc\"><Value>Y</Value></Field><Field Name=\"current-run-id\"><Value>1058</Value></Field><Field Name=\"is-unlink-needed\"><Value/></Field></Fields><RelatedEntities/></Entity>";
    final byte[] FINISHED_DATA = String.format(TIMESLOT_DATA_FORMAT, "Finished").getBytes();
    final byte[] RUNNING_DATA = String.format(TIMESLOT_DATA_FORMAT, "Running").getBytes();
    final byte[] EVENT_LOG_DATA =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Entities TotalResults=\"7\"><Entity Type=\"event-log-read\"><Fields><Field Name=\"id\"><Value>1798</Value></Field><Field Name=\"description\"><Value>Timeslot ID '1005' was created successfully</Value></Field><Field Name=\"action\"><Value>Create Timeslot</Value></Field><Field Name=\"creation-time\"><Value>2013-02-19 12:03:42</Value></Field><Field Name=\"event-type\"><Value>Info</Value></Field></Fields><RelatedEntities/></Entity><Entity Type=\"event-log-read\"><Fields><Field Name=\"id\"><Value>1800</Value></Field><Field Name=\"description\"><Value>Creating run-time data for run '1036' of 'Test Set' '1' (Timeslot ID '1005'; BVS ID '1036')</Value></Field><Field Name=\"action\"><Value>Create run-time data</Value></Field><Field Name=\"creation-time\"><Value>2013-02-19 12:03:43</Value></Field><Field Name=\"event-type\"><Value>Info</Value></Field></Fields><RelatedEntities/></Entity><Entity Type=\"event-log-read\"><Fields><Field Name=\"id\"><Value>1801</Value></Field><Field Name=\"description\"><Value>TestSet ID: '1036' start time: '2013-02-19 12:03:43.907'</Value></Field><Field Name=\"action\"><Value>BVS Run</Value></Field><Field Name=\"creation-time\"><Value>2013-02-19 12:03:43</Value></Field><Field Name=\"event-type\"><Value>Info</Value></Field></Fields><RelatedEntities/></Entity><Entity Type=\"event-log-read\"><Fields><Field Name=\"id\"><Value>1802</Value></Field><Field Name=\"description\"><Value>Host 'vmltqa63' failed to run task '1244' of type 'hp.alm.test-execution'. Cause: The testing tool is not installed - ALM Lab service could not execute  test VAPI-XP because : ALM Lab service could not connect to the testing tool for Check host because Can't initialize host service process</Value></Field><Field Name=\"action\"><Value>Host Fail</Value></Field><Field Name=\"creation-time\"><Value>2013-02-19 12:03:50</Value></Field><Field Name=\"event-type\"><Value>Error</Value></Field></Fields><RelatedEntities/></Entity><Entity Type=\"event-log-read\"><Fields><Field Name=\"id\"><Value>1803</Value></Field><Field Name=\"description\"><Value>Host 'vmltqa63' ('1068') is about to become non operational and be replaced. The host is used by BVS run '1036' of BVS '1036' (timeslot '1005'). Reason: ALM Lab service could not execute  test VAPI-XP because : ALM Lab service could not connect to the testing tool for Check host because Can't initialize host service process</Value></Field><Field Name=\"action\"><Value>Host non operational</Value></Field><Field Name=\"creation-time\"><Value>2013-02-19 12:03:50</Value></Field><Field Name=\"event-type\"><Value>Error</Value></Field></Fields><RelatedEntities/></Entity><Entity Type=\"event-log-read\"><Fields><Field Name=\"id\"><Value>1805</Value></Field><Field Name=\"description\"><Value>Host 'vmltqa63' ('1068') became non operational and was replaced by host 'effi2' ('1065'). The host is assigned to timeslot '1005'</Value></Field><Field Name=\"action\"><Value>Host non operational</Value></Field><Field Name=\"creation-time\"><Value>2013-02-19 12:03:50</Value></Field><Field Name=\"event-type\"><Value>Error</Value></Field></Fields><RelatedEntities/></Entity><Entity Type=\"event-log-read\"><Fields><Field Name=\"id\"><Value>1808</Value></Field><Field Name=\"description\"><Value>Timeslot ID '1005' was closed</Value></Field><Field Name=\"action\"><Value>Close Timeslot</Value></Field><Field Name=\"creation-time\"><Value>2013-02-19 12:04:17</Value></Field><Field Name=\"event-type\"><Value>Info</Value></Field></Fields><RelatedEntities/></Entity></Entities>".getBytes();
    
}
