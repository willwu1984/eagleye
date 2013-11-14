package eagleye.output;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import eagleye.MonitorWatcherRegister;
import eagleye.PeriodMonitorable;
import eagleye.addons.jvm.GarbageCollectorMonitor;
import eagleye.addons.jvm.MemInfoMonitor;
import eagleye.addons.jvm.ThreadInfoMonitor;
import eagleye.component.PeriodMonitorInfo;

/**
 * @author wuwei
 * @since 1.0.0.0
 */
public class MonitorFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String uri = req.getRequestURI();

		Gson gson=new Gson();
		HashMap<String , Long> map = new HashMap<String , Long>();
		if (uri.equals(req.getContextPath() + "/monitor/monitorInfo.action")) {
			Set<PeriodMonitorable> periodMonitors = MonitorWatcherRegister
					.periodMonitors();
			String monitorInfo="";
			for (PeriodMonitorable periodMonitorable : periodMonitors) {
				if (periodMonitorable instanceof ThreadInfoMonitor) {
					monitorInfo += "Thread info:\n";
					PeriodMonitorInfo info=periodMonitorable.doMonitorEagleye();
					map.putAll(gson.fromJson(info.toString(), map.getClass()));
				}
				if (periodMonitorable instanceof MemInfoMonitor) {
					monitorInfo += "Memory info:\n";
					PeriodMonitorInfo info=periodMonitorable.doMonitorEagleye();
					map.putAll(gson.fromJson(info.toString(), map.getClass()));
				}
				if (periodMonitorable instanceof GarbageCollectorMonitor) {
					monitorInfo += "GarbageCollector info:\n";
					PeriodMonitorInfo info=periodMonitorable.doMonitorEagleye();
					map.putAll(gson.fromJson(info.toString(), map.getClass()));
				}
			}
			//output the monitor info
			response.getWriter().println(gson.toJson(map));
			response.getWriter().close();
		} else {
			chain.doFilter(request, response);
		}

	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
