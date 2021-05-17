import React, { useEffect, useState } from "react";
import { Switch, Route, Link, useLocation, Redirect } from "react-router-dom";

import {
  Chat,
  Header,
  Page,
  SidebarNav,
  PageAlert,
  PageLoaderProvider,
  PageAlertProvider,
} from "components/common";
import { NotFound } from "components/pages";
import avatar1 from "assets/images/avatar1.png";
import usePrevious from "utils/usePrevious";
import appRoutes from "appRoutes";
import handleKeyAccessibility, {
  handleClickAccessibility,
} from "utils/handleTabAccessibility";

const MOBILE_SIZE = 992;

export default function Home() {
  const location = useLocation();
  const prevLocation = usePrevious(location);
  const [sidebarCollapsed, setSidebarCollapsed] = useState(false);
  const [isMobile, setIsMobile] = useState(window.innerWidth <= MOBILE_SIZE);
  const [showChat1, setShowChat1] = useState(true);

  function handleResize() {
    if (window.innerWidth <= MOBILE_SIZE) {
      setSidebarCollapsed(false);
      setIsMobile(true);
    } else {
      setIsMobile(false);
    }
  }

  function toggleSideCollapse() {
    setSidebarCollapsed(!sidebarCollapsed);
  }

  function closeChat() {
    setShowChat1(false);
  }

  useEffect(() => {
    if (
      isMobile &&
      prevLocation &&
      !location.pathname.includes(prevLocation.pathname)
    ) {
      toggleSideCollapse();
    }
  });

  useEffect(() => {
    window.addEventListener("resize", handleResize);
    document.addEventListener("keydown", handleKeyAccessibility);
    document.addEventListener("click", handleClickAccessibility);

    return () => {
      window.removeEventListener("resize", handleResize);
      document.removeEventListener("keydown", handleKeyAccessibility);
      document.removeEventListener("click", handleClickAccessibility);
    };
  }, []);

  return (
    <PageLoaderProvider>
      <PageAlertProvider>
        <div className={`app ${sidebarCollapsed ? "side-menu-collapsed" : ""}`}>
          <PageAlert />
          <div className="app-body">
            <SidebarNav
              isSidebarCollapsed={sidebarCollapsed}
              toggleSidebar={toggleSideCollapse}
            />
            <Page>
              <Header
                isSidebarCollapsed={sidebarCollapsed}
                toggleSidebar={toggleSideCollapse}
              />

              <main id="primary-content" tabIndex="-1" role="main">
                <Switch>
                  {appRoutes.map((page, key) => (
                    <Route
                      path={page.path}
                      exact
                      component={page.component}
                      key={key}
                    />
                  ))}
                  <Route path="404" component={NotFound} />
                  <Route path="*">
                    <Redirect to="/404" />
                  </Route>
                </Switch>
              </main>
            </Page>
          </div>

          <footer className="app-footer">
            <span>Copyright © 2019 Nice Dash. All rights reserved.</span>
            <span>
              <Link to="">Terms</Link> | <Link to="">Privacy Policy</Link>
            </span>
            <span className="ml-auto hidden-xs">
              Made with{" "}
              <span role="img" aria-label="taco">
                🌮
              </span>
            </span>
          </footer>

          <Chat.Container>
            {showChat1 && (
              <Chat.ChatBox
                name="Messages"
                status="online"
                image={avatar1}
                close={closeChat}
              />
            )}
          </Chat.Container>
        </div>
      </PageAlertProvider>
    </PageLoaderProvider>
  );
}
