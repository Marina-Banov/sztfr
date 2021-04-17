import React from 'react';
import ToggleSidebarButton from './ToggleSidebarButton';
import PageLoader from '../PageLoader/PageLoader';
import {Navbar, Nav, UncontrolledDropdown, DropdownToggle, DropdownMenu, DropdownItem, Badge} from 'reactstrap';
import { matchPath } from 'react-router-dom';
import {Avatar} from "../../index";
import {useFirebase} from "../../../firebase";
import routes from "../../../components/home-vibe/routes";

export default function Header({toggleSidebar, isSidebarCollapsed}) {
  function getPageTitle() {
    let name;
    routes.map(prop => {
      if (
        matchPath(window.location.pathname, {
          path: prop.path,
          exact: true,
          strict: false
        })
      ) {
        name = prop.name;
      }
      return null;
    });
    return name;
  }

    return (
      <header className="app-header">
        <SkipToContentLink focusId="primary-content" />
        <div className="top-nav">
          <Navbar color="faded" light expand="md">
            <ToggleSidebarButton
              toggleSidebar={toggleSidebar}
              isSidebarCollapsed={isSidebarCollapsed}
            />
            <div className="page-heading">{getPageTitle()}</div>
              <Nav className="ml-auto" navbar vertical="false">
                <HeaderNav />
              </Nav>
            <PageLoader />
          </Navbar>
        </div>
      </header>
    );
}

function HeaderNav() {
  const firebase = useFirebase();

  return (
      <React.Fragment>
        <UncontrolledDropdown nav inNavbar>
          <DropdownToggle nav caret>
            New
          </DropdownToggle>
          <DropdownMenu right>
            <DropdownItem>Project</DropdownItem>
            <DropdownItem>User</DropdownItem>
            <DropdownItem divider />
            <DropdownItem>
              Message <Badge color="primary">10</Badge>
            </DropdownItem>
          </DropdownMenu>
        </UncontrolledDropdown>
        <UncontrolledDropdown nav inNavbar>
            <DropdownToggle nav>
            <Avatar size="small" color="#3E408B"
                    initials={firebase.auth.currentUser.email[0]}
                    image={firebase.auth.currentUser.photoURL}/>
          </DropdownToggle>
          <DropdownMenu right>
            <DropdownItem onClick={() => firebase.logOut()}>Odjava</DropdownItem>
          </DropdownMenu>
        </UncontrolledDropdown>
      </React.Fragment>
  );
}

function SkipToContentLink({ focusId }) {
  return (
    <a href={`#${focusId}`} tabIndex="1" className="skip-to-content">
      Skip to Content
    </a>
  );
}
