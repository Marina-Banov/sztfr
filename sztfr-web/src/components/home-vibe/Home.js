import React, { useEffect, useState } from "react";
import { Switch, Route, Link, useLocation } from "react-router-dom";

import {
  Header,
  SidebarNav,
  Footer,
  PageContent,
  Chat,
  PageAlert,
  Page,
} from "vibe";
import ContextProviders from "vibe/components/ContextProviders";
import avatar1 from "assets/images/avatar1.png";
import usePrevious from "utils/usePrevious";
import routes from "./routes";
import handleKeyAccessibility, {
  handleClickAccessibility,
} from "./handleTabAccessibility";

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
    <ContextProviders>
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
            <PageContent>
              <Switch>
                {routes.map((page, key) => (
                  <Route
                    path={page.path}
                    exact
                    component={page.component}
                    key={key}
                  />
                ))}
              </Switch>
            </PageContent>
          </Page>
        </div>
        <Footer>
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
        </Footer>
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
    </ContextProviders>
  );
}
