<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<ul class="nav flex-column long-bg">
    <li class="nav-item">
        <a class="nav-link" href="/dashboard">
            <span>Pulpit</span>
            <i class="fas fa-angle-right"></i>
        </a>
    </li>
    <li class="nav-item">
        <a class="nav-link" href="/recipes">
            <span>Przepisy</span>
            <i class="fas fa-angle-right"></i>
        </a>
    </li>
    <li class="nav-item">
        <a class="nav-link" href="/schedules">
            <span>Plany</span>
            <i class="fas fa-angle-right"></i>
        </a>
    </li>
    <li class="nav-item">
        <a class="nav-link" href="/edit-admin">
            <span>Edytuj dane</span>
            <i class="fas fa-angle-right"></i>
        </a>
    </li>
    <li class="nav-item">
        <a class="nav-link disabled" href="/edit-password">
            <span>Zmień hasło</span>
            <i class="fas fa-angle-right"></i>
        </a>
    </li>

    <li class="nav-item">
        <a class="nav-link" href="/super-admin-users">
            <span>Użytkownicy</span>
            <i class="fas fa-angle-right"></i>
        </a>
    </li>
</ul>
